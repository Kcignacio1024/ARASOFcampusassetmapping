package ph.edu.batstateu.arasof.arasofcampusassetmapping.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isShiftPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel

private const val MAX_ZOOM = 10f

fun AssetType.color(): Color = when (this) {
    AssetType.EXIT -> Color(0xFF2E7D32)
    AssetType.FIRE_ALARM -> Color(0xFFC62828)
    AssetType.FIRE_LIGHT -> Color(0xFFF9A825)
    AssetType.HYDRANT -> Color(0xFF1565C0)
    AssetType.EXTINGUISHER -> Color(0xFFEF6C00)
    AssetType.AIRCON -> Color(0xFF00838F)
    AssetType.WIFI -> Color(0xFF8E24AA)
    AssetType.PROJECTOR -> Color(0xFF455A64)
    AssetType.CCTV -> Color(0xFF607D8B)
    AssetType.COMPUTER -> Color(0xFF5E35B1)
    AssetType.SPECIAL_EQUIP -> Color(0xFF6D4C41)
    AssetType.TABLE_SET -> Color(0xFF8D6E63)
    AssetType.SEAT -> Color(0xFFEF8F00)
    AssetType.TABLE -> Color(0xFF00ACC1)
    AssetType.FAN -> Color(0xFF26A69A)
    AssetType.SPRINKLER -> Color(0xFF039BE5)
    AssetType.WHITEBOARD -> Color(0xFF757575)
    AssetType.MICROPHONE -> Color(0xFFD81B60)
    AssetType.MIC_STAND -> Color(0xFFAD1457)
    AssetType.SPEAKER -> Color(0xFF3949AB)
    AssetType.BASKETBALL_BOARD -> Color(0xFFE64A19)
    AssetType.BASKETBALL -> Color(0xFFF4511E)
    AssetType.VOLLEYBALL -> Color(0xFF7CB342)
    AssetType.ATM -> Color(0xFF1E88E5)
    AssetType.ID_SCANNER -> Color(0xFF00897B)
}

private fun clampOffset(o: Offset, s: Float, size: IntSize): Offset {
    val mx = size.width * (s - 1f) / 2f
    val my = size.height * (s - 1f) / 2f
    return Offset(o.x.coerceIn(-mx, mx), o.y.coerceIn(-my, my))
}

@Composable
fun FloorPlanScreen(vm: CampusViewModel) {
    val building = vm.selectedBuilding ?: return
    val floor = vm.selectedFloor
    val data = remember(building, floor) { CampusData.floorData(building, floor) }
    var selectedAsset by remember { mutableStateOf<Asset?>(null) }
    var highlightedAsset by remember { mutableStateOf<Asset?>(null) }
    var selectedRoom by remember { mutableStateOf<Room?>(null) }
    var onlyIssues by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var showFloorAssets by remember { mutableStateOf(false) }
    val cs = MaterialTheme.colorScheme

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val coroutineScope = rememberCoroutineScope()

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    // Smoothly center and focus camera on highlighted asset
    LaunchedEffect(highlightedAsset) {
        highlightedAsset?.let { a ->
            if (boxSize.width > 0 && boxSize.height > 0) {
                val targetScale = 3.5f
                val ratio = data.aspect
                val planW = minOf(boxSize.width.toFloat(), boxSize.height.toFloat() * ratio)
                val planH = planW / ratio
                val ax = planW * a.x
                val ay = planH * a.y
                val targetOffsetX = boxSize.width / 2f - ax * targetScale
                val targetOffsetY = boxSize.height / 2f - ay * targetScale
                val targetOffset = clampOffset(Offset(targetOffsetX, targetOffsetY), targetScale, boxSize)

                val animScale = Animatable(scale)
                val animOffset = Animatable(offset, Offset.VectorConverter)

                coroutineScope.launch {
                    animScale.animateTo(targetScale, spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)) { scale = value }
                }
                coroutineScope.launch {
                    animOffset.animateTo(targetOffset, spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessLow)) { offset = value }
                }
            }
        }
    }

    fun zoomAt(focus: Offset, factor: Float) {
        val c = Offset(boxSize.width / 2f, boxSize.height / 2f)
        val ns = (scale * factor).coerceIn(1f, MAX_ZOOM)
        val rel = focus - c
        offset = clampOffset(rel - (rel - offset) * (ns / scale), ns, boxSize)
        scale = ns
    }

    val visibleAssets = data.assets.filter { vm.isMaintenance || it.type.studentVisible }
    val shownAssets = visibleAssets.filter {
        it.showIcon && (
                if (vm.showAssetIcons) (!onlyIssues || vm.openIncidentFor(it.id) != null)
                else vm.openIncidentFor(it.id) != null
                )
    }
    val legendTypes = AssetType.values().filter { t -> visibleAssets.any { it.type == t && it.showIcon } }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = vm::back) { Text("← Map") }
            Text(
                "${building.id}. ${building.name}", style = MaterialTheme.typography.titleMedium,
                maxLines = 2, modifier = Modifier.weight(1f)
            )
            TextButton(onClick = { showSettings = true }) { Text("Settings") }
        }
        if (building.floors.size > 1) {
            Row(
                Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                building.floors.forEach { f ->
                    FilterChip(selected = f == floor, onClick = { vm.switchFloor(f); highlightedAsset = null }, label = { Text(f.label) })
                }
            }
        } else {
            Text("Ground floor only", style = MaterialTheme.typography.labelMedium, color = cs.onSurfaceVariant)
        }
        if (vm.isMaintenance) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = onlyIssues, onCheckedChange = { onlyIssues = it })
                Spacer(Modifier.width(8.dp))
                Text("Show only reported issues")
            }
        }
        val hasWholeFloorRoom = data.rooms.any { it.w >= 0.99f && it.h >= 0.99f }
        if (!hasWholeFloorRoom) {
            FilledTonalButton(onClick = { showFloorAssets = true }, modifier = Modifier.fillMaxWidth()) {
                Text("All assets on this floor (${visibleAssets.size})")
            }
        }
        data.note?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant) }
        Spacer(Modifier.height(6.dp))

        BoxWithConstraints(
            Modifier.weight(1f).fillMaxWidth()
                .border(1.dp, cs.outlineVariant).background(cs.surface)
        ) {
            val planW = minOf(maxWidth, maxHeight * data.aspect)
            val planH = planW / data.aspect

            Box(
                Modifier.fillMaxSize()
                    .clipToBounds()
                    .onSizeChanged { boxSize = it }
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, _ ->
                            val c = Offset(size.width / 2f, size.height / 2f)
                            val ns = (scale * zoom).coerceIn(1f, MAX_ZOOM)
                            val rel = centroid - c
                            val moved = rel - (rel - offset) * (ns / scale) + pan
                            scale = ns
                            offset = clampOffset(moved, ns, size)
                        }
                    }
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                if (event.type == PointerEventType.Scroll) {
                                    val ch = event.changes.first()
                                    val d = ch.scrollDelta
                                    if (event.keyboardModifiers.isShiftPressed) {
                                        val move = if (d.x != 0f) d.x else d.y
                                        offset = clampOffset(offset + Offset(-move * 60f, 0f), scale, size)
                                        ch.consume()
                                    } else if (d.y != 0f) {
                                        zoomAt(ch.position, if (d.y < 0f) 1.2f else 1f / 1.2f)
                                        ch.consume()
                                    }
                                }
                            }
                        }
                    }
            ) {
                Box(
                    Modifier.align(Alignment.Center).size(planW, planH)
                        .graphicsLayer(
                            scaleX = scale, scaleY = scale,
                            translationX = offset.x, translationY = offset.y
                        )
                        .border((1.5f / sIf(scale)).dp, cs.outline)
                ) {
                    // corridors / stairs
                    data.zones.forEach { z ->
                        val zw = planW * z.w
                        val zh = planH * z.h
                        val zShape: Shape =
                            if (z.outline.isEmpty()) RectangleShape else polyShape(z.outline, z.x, z.y, z.w, z.h)
                        Box(
                            Modifier.offset(planW * z.x, planH * z.y).size(zw, zh).background(
                                when (z.kind) {
                                    ZoneKind.CORRIDOR -> cs.surfaceVariant.copy(alpha = 0.55f)
                                    ZoneKind.STAIRS -> cs.tertiaryContainer.copy(alpha = 0.7f)
                                    ZoneKind.ROOFTOP -> cs.surfaceVariant.copy(alpha = 0.35f)
                                    ZoneKind.RAMP -> Color(0xFFFFCC80).copy(alpha = 0.7f)
                                    ZoneKind.PLAIN -> cs.secondaryContainer.copy(alpha = 0.6f)
                                },
                                zShape
                            ),
                            contentAlignment = Alignment.Center
                        ) { ScaledLabel(z.name, zw, zh, scale, vm.roomTextSp * 0.8f) }
                    }
                    // rooms
                    data.rooms.forEach { r ->
                        val rw = planW * r.w
                        val rh = planH * r.h
                        val shape: Shape =
                            if (r.outline.isEmpty()) RectangleShape else polyShape(r.outline, r.x, r.y, r.w, r.h)
                        Box(
                            Modifier.offset(planW * r.x, planH * r.y).size(rw, rh)
                                .then(if (r.outline.isEmpty()) Modifier else Modifier.clip(shape))
                                .background(cs.primaryContainer.copy(alpha = 0.35f), shape)
                                .border((1.2f / sIf(scale)).dp, cs.outline, shape)
                                .clickable { selectedRoom = r },
                            contentAlignment = Alignment.Center
                        ) { ScaledLabel(r.name, rw, rh, scale, vm.roomTextSp) }
                    }
                    // assets
                    val f = if (scale >= 3f) 1f else 0.6f + 0.2f * (scale - 1f)
                    shownAssets.forEach { a ->
                        AssetMarker(
                            asset = a, hasIssue = vm.openIncidentFor(a.id) != null,
                            onClick = { selectedAsset = a },
                            modifier = Modifier
                                .offset(planW * a.x - 16.dp, planH * a.y - 16.dp)
                                .graphicsLayer(scaleX = f / scale, scaleY = f / scale)
                        )
                    }

                    // Pulsing ring highlight for located asset
                    highlightedAsset?.let { ha ->
                        if (ha.floor == floor && ha.buildingId == building.id) {
                            Canvas(Modifier.fillMaxSize()) {
                                val ax = size.width * ha.x
                                val ay = size.height * ha.y
                                val accent = Color(0xFFFF6F00)
                                drawCircle(
                                    color = accent.copy(alpha = pulseAlpha),
                                    radius = 36f,
                                    center = Offset(ax, ay),
                                    style = Stroke(width = 4f)
                                )
                                drawCircle(
                                    color = accent,
                                    radius = 16f,
                                    center = Offset(ax, ay)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
        if (vm.showAssetIcons) {
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                legendTypes.forEach { t ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(20.dp).clip(CircleShape).background(t.color()), contentAlignment = Alignment.Center) {
                            Text(t.symbol, color = Color.White, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.width(4.dp)); Text(t.label, fontSize = 12.sp)
                    }
                }
            }
        }
        Text(
            "Tap a room to list its assets individually, with tags and 'Locate on plan'.",
            fontSize = 11.sp, color = cs.onSurfaceVariant
        )
    }

    if (showSettings) SettingsDialog(vm) { showSettings = false }
    selectedRoom?.let { room ->
        RoomSheet(
            room = room,
            assets = data.assets.filter { it.roomId == room.id && (vm.isMaintenance || it.type.studentVisible) },
            vm = vm,
            onAssetClick = { selectedRoom = null; selectedAsset = it },
            onLocateOnPlan = { asset ->
                selectedRoom = null
                highlightedAsset = asset
            },
            onDismiss = { selectedRoom = null }
        )
    }
    if (showFloorAssets) {
        val floorRoom = Room(
            id = "${building.id}-${floor.ordinal}-ALL", buildingId = building.id, floor = floor,
            name = "${floor.label} - all assets", x = 0f, y = 0f, w = 1f, h = 1f,
            computers = data.rooms.sumOf { it.computers }
        )
        RoomSheet(
            room = floorRoom,
            assets = visibleAssets,
            vm = vm,
            onAssetClick = { showFloorAssets = false; selectedAsset = it },
            onLocateOnPlan = { asset ->
                showFloorAssets = false
                highlightedAsset = asset
            },
            onDismiss = { showFloorAssets = false }
        )
    }
    selectedAsset?.let { AssetDetailsDialog(it, vm) { selectedAsset = null } }
}

private fun sIf(s: Float) = s

private fun polyShape(points: List<Pair<Float, Float>>, x: Float, y: Float, w: Float, h: Float): Shape =
    GenericShape { size, _ ->
        points.forEachIndexed { i, (px, py) ->
            val sx = (px - x) / w * size.width
            val sy = (py - y) / h * size.height
            if (i == 0) moveTo(sx, sy) else lineTo(sx, sy)
        }
        close()
    }

@Composable
private fun ScaledLabel(text: String, w: Dp, h: Dp, s: Float, targetSp: Float) {
    val size = fitFontSize(text, (w * s).value - 8f, (h * s).value - 4f, targetSp, 9f) ?: return
    Box(
        Modifier.requiredSize(w * s, h * s).graphicsLayer(scaleX = 1f / s, scaleY = 1f / s),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text, fontSize = size.sp, lineHeight = (size * 1.15f).sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, maxLines = 4, overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                .padding(horizontal = 4.dp, vertical = 1.dp)
        )
    }
}

private fun fitFontSize(text: String, availW: Float, availH: Float, targetSp: Float, minSp: Float): Float? {
    if (availW < 24f || availH < 12f) return null
    val words = text.split(' ').filter { it.isNotEmpty() }
    var sp = targetSp
    while (sp >= minSp) {
        val maxChars = (availW / (sp * 0.58f)).toInt()
        if (maxChars >= 1 && words.none { it.length > maxChars }) {
            var lines = 1
            var cur = 0
            for (word in words) {
                if (cur == 0) cur = word.length
                else if (cur + 1 + word.length <= maxChars) cur += 1 + word.length
                else { lines++; cur = word.length }
            }
            if (lines <= 4 && lines * sp * 1.2f <= availH) return sp
        }
        sp -= 1f
    }
    return null
}

@Composable
private fun AssetMarker(asset: Asset, hasIssue: Boolean, onClick: () -> Unit, modifier: Modifier) {
    Box(modifier.size(32.dp), contentAlignment = Alignment.Center) {
        Box(
            Modifier.size(26.dp).clip(CircleShape).background(asset.type.color())
                .border(if (hasIssue) 3.dp else 1.dp, if (hasIssue) Color.Black else Color.White, CircleShape)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) { Text(asset.type.symbol, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold) }
        if (hasIssue) {
            Box(
                Modifier.align(Alignment.TopEnd).size(13.dp).clip(CircleShape).background(Color.Black),
                contentAlignment = Alignment.Center
            ) { Text("!", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold) }
        }
    }
}

@Composable
fun AssetDetailsDialog(asset: Asset, vm: CampusViewModel, onDismiss: () -> Unit) {
    val incident = vm.openIncidentFor(asset.id)
    var isEditing by remember { mutableStateOf(false) }

    var tag by remember { mutableStateOf(asset.tag) }
    var locationNote by remember { mutableStateOf(asset.locationNote ?: "") }
    var brand by remember { mutableStateOf(asset.brand ?: "") }
    var model by remember { mutableStateOf(asset.model ?: "") }
    var serialNo by remember { mutableStateOf(asset.serialNo ?: "") }
    var installedOn by remember { mutableStateOf(asset.installedOn ?: "") }
    var condition by remember { mutableStateOf(asset.condition) }
    var lastServiceOn by remember { mutableStateOf(asset.lastServiceOn ?: "") }

    var reportText by remember { mutableStateOf("") }
    var fixNote by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(asset.label, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                if (asset.tag.isNotBlank()) {
                    Surface(shape = RoundedCornerShape(6.dp), color = MaterialTheme.colorScheme.primaryContainer) {
                        Text(asset.tag, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }
        },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.heightIn(max = 450.dp)) {
                item {
                    Text(asset.type.label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                }

                if (isEditing && vm.isMaintenance) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(value = tag, onValueChange = { tag = it }, label = { Text("Asset Tag") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = locationNote, onValueChange = { locationNote = it }, label = { Text("Location Note") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Brand") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = serialNo, onValueChange = { serialNo = it }, label = { Text("Serial No") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = installedOn, onValueChange = { installedOn = it }, label = { Text("Installed On (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = lastServiceOn, onValueChange = { lastServiceOn = it }, label = { Text("Last Service On (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth())

                            Text("Condition", style = MaterialTheme.typography.labelMedium)
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                AssetCondition.values().forEach { cond ->
                                    FilterChip(
                                        selected = condition == cond,
                                        onClick = { condition = cond },
                                        label = { Text(cond.label) }
                                    )
                                }
                            }
                        }
                    }
                } else {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                DetailRow("Tag", asset.tag.ifBlank { "N/A" })
                                DetailRow("Location Note", asset.locationNote?.ifBlank { null } ?: "None")
                                DetailRow("Brand", asset.brand?.ifBlank { null } ?: "N/A")
                                DetailRow("Model", asset.model?.ifBlank { null } ?: "N/A")
                                DetailRow("Serial No", asset.serialNo?.ifBlank { null } ?: "N/A")
                                DetailRow("Installed On", asset.installedOn?.ifBlank { null } ?: "N/A")
                                DetailRow("Condition", asset.condition.label)
                                DetailRow("Last Service On", asset.lastServiceOn?.ifBlank { null } ?: "N/A")
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(4.dp))
                    Text("Open Reports & Incidents", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                }

                if (incident != null) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("Reported by ${incident.reportedBy}", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onErrorContainer)
                                Text(incident.description, color = MaterialTheme.colorScheme.onErrorContainer)
                                if (incident.alsoReportedBy.isNotEmpty()) {
                                    Text(
                                        "Also reported by ${incident.alsoReportedBy.joinToString(", ")} · ${incident.reportCount} people in total",
                                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                }
                                if (!vm.isMaintenance) {
                                    if (!vm.hasReported(incident)) {
                                        OutlinedButton(onClick = { vm.confirmIssue(incident) }) { Text("I have this problem too") }
                                    } else {
                                        Text(
                                            "You reported this problem. Maintenance has been notified.",
                                            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer
                                        )
                                    }
                                }
                                if (vm.isMaintenance) {
                                    Spacer(Modifier.height(4.dp))
                                    OutlinedTextField(
                                        value = fixNote,
                                        onValueChange = { fixNote = it },
                                        label = { Text("Fix note") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Button(onClick = { vm.resolve(incident, fixNote); onDismiss() }) {
                                        Text("Mark as fixed")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    item {
                        if (!isEditing) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("No open issues.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                OutlinedTextField(
                                    value = reportText,
                                    onValueChange = { reportText = it },
                                    label = { Text("Report a problem with this asset") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Button(
                                    onClick = { vm.reportIssue(asset, reportText); onDismiss() },
                                    enabled = reportText.isNotBlank()
                                ) {
                                    Text("Submit Report")
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (vm.isMaintenance) {
                if (isEditing) {
                    Button(onClick = {
                        asset.tag = tag
                        asset.locationNote = locationNote.ifBlank { null }
                        asset.brand = brand.ifBlank { null }
                        asset.model = model.ifBlank { null }
                        asset.serialNo = serialNo.ifBlank { null }
                        asset.installedOn = installedOn.ifBlank { null }
                        asset.condition = condition
                        asset.lastServiceOn = lastServiceOn.ifBlank { null }
                        isEditing = false
                    }) {
                        Text("Save Changes")
                    }
                } else {
                    Button(onClick = { isEditing = true }) {
                        Text("Edit Asset")
                    }
                }
            } else {
                TextButton(onClick = onDismiss) { Text("Close") }
            }
        },
        dismissButton = {
            if (isEditing) {
                TextButton(onClick = { isEditing = false }) { Text("Cancel") }
            } else {
                TextButton(onClick = onDismiss) { Text("Close") }
            }
        }
    )
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
    }
}