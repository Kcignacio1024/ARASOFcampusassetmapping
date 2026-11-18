package ph.edu.batstateu.arasof.arasofcampusassetmapping.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel

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
}

@Composable
fun FloorPlanScreen(vm: CampusViewModel) {
    val building = vm.selectedBuilding ?: return
    val floor = vm.selectedFloor
    val data = remember(building, floor) { CampusData.floorData(building, floor) }
    var selectedAsset by remember { mutableStateOf<Asset?>(null) }
    var selectedRoom by remember { mutableStateOf<Room?>(null) }
    var onlyIssues by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    val cs = MaterialTheme.colorScheme

    // Different map per user type: students never see IT / security assets (CCTV, computers, equipment).
    val visibleAssets = data.assets.filter { vm.isMaintenance || it.type.studentVisible }
    // Settings: asset icons off = no icons on the plan (assets are still listed when a room is tapped)
    val shownAssets =
        if (!vm.showAssetIcons) emptyList()
        else visibleAssets.filter { !onlyIssues || vm.openIncidentFor(it.id) != null }
    val legendTypes = AssetType.values().filter { t -> visibleAssets.any { it.type == t } }

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
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                building.floors.forEach { f ->
                    FilterChip(selected = f == floor, onClick = { vm.switchFloor(f) }, label = { Text(f.label) })
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
        data.note?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = cs.onSurfaceVariant) }
        Spacer(Modifier.height(6.dp))

        BoxWithConstraints(
            Modifier.weight(1f).fillMaxWidth()
                .border(1.dp, cs.outlineVariant).background(cs.surface)
        ) {
            val planW = minOf(maxWidth, maxHeight * data.aspect)
            val planH = planW / data.aspect

            ZoomPanBox(Modifier.fillMaxSize()) { s ->
                Box(
                    Modifier.align(Alignment.Center).size(planW, planH)
                        .border((1.5f / s).dp, cs.outline)
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
                        ) { ScaledLabel(z.name, zw, zh, s, vm.roomTextSp * 0.8f) }
                    }
                    // rooms (tap for details)
                    data.rooms.forEach { r ->
                        val rw = planW * r.w
                        val rh = planH * r.h
                        // L-shaped rooms use their outline so they never overlap their neighbours
                        val shape: Shape =
                            if (r.outline.isEmpty()) RectangleShape else polyShape(r.outline, r.x, r.y, r.w, r.h)
                        Box(
                            Modifier.offset(planW * r.x, planH * r.y).size(rw, rh)
                                .then(if (r.outline.isEmpty()) Modifier else Modifier.clip(shape))
                                .background(cs.primaryContainer.copy(alpha = 0.35f), shape)
                                .border((1.2f / s).dp, cs.outline, shape)
                                .clickable { selectedRoom = r },
                            contentAlignment = Alignment.Center
                        ) { ScaledLabel(r.name, rw, rh, s, vm.roomTextSp) }
                    }
                    // assets: keep a readable, constant size while zooming
                    val f = if (s >= 3f) 1f else 0.6f + 0.2f * (s - 1f)
                    shownAssets.forEach { a ->
                        AssetMarker(
                            asset = a, hasIssue = vm.openIncidentFor(a.id) != null,
                            onClick = { selectedAsset = a },
                            modifier = Modifier
                                .offset(planW * a.x - 16.dp, planH * a.y - 16.dp)
                                .graphicsLayer(scaleX = f / s, scaleY = f / s)
                        )
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
            if (vm.showAssetIcons)
                "Zoom in to see all room names. Tap a room for details, or an asset to report a problem. " +
                        "A black ring with ! means a problem was reported."
            else
                "Asset icons are off (Settings). Tap a room to see its assets and report a problem.",
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
            onDismiss = { selectedRoom = null }
        )
    }
    selectedAsset?.let { AssetDialog(it, vm) { selectedAsset = null } }
}

/** Shape from a polygon given in plan fractions, relative to the box (x, y, w, h). */
private fun polyShape(points: List<Pair<Float, Float>>, x: Float, y: Float, w: Float, h: Float): Shape =
    GenericShape { size, _ ->
        points.forEachIndexed { i, (px, py) ->
            val sx = (px - x) / w * size.width
            val sy = (py - y) / h * size.height
            if (i == 0) moveTo(sx, sy) else lineTo(sx, sy)
        }
        close()
    }

/**
 * A room name that stays the same readable size while the plan is zoomed. It uses the size chosen in
 * Settings, and shrinks a little only if the room is too small for it. It is hidden when even the
 * smallest size does not fit (zoom in to reveal it).
 */
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

/** Largest font size (sp) from [targetSp] down to [minSp] at which [text] fits a box (dp), or null. */
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
fun AssetDialog(asset: Asset, vm: CampusViewModel, onDismiss: () -> Unit) {
    val incident = vm.openIncidentFor(asset.id)
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(asset.label) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(asset.type.label, style = MaterialTheme.typography.labelMedium)
                if (incident == null) {
                    Text(if (vm.isMaintenance) "No open issues. This asset is marked as working." else "Is something wrong with this asset?")
                    OutlinedTextField(
                        value = text, onValueChange = { text = it },
                        label = { Text("Describe the problem") }, modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text("Reported by ${incident.reportedBy}", fontWeight = FontWeight.Medium)
                    Text(incident.description)
                    if (vm.isMaintenance) {
                        OutlinedTextField(
                            value = text, onValueChange = { text = it },
                            label = { Text("Fix note (optional)") }, modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text("Maintenance has been notified and will update this once fixed.")
                    }
                }
            }
        },
        confirmButton = {
            when {
                incident == null -> Button(onClick = { vm.reportIssue(asset, text); onDismiss() }) { Text("Report problem") }
                vm.isMaintenance -> Button(onClick = { vm.resolve(incident, text); onDismiss() }) { Text("Mark as fixed") }
                else -> TextButton(onClick = onDismiss) { Text("OK") }
            }
        },
        dismissButton = { if (incident == null || vm.isMaintenance) TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}