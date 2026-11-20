package ph.edu.batstateu.arasof.arasofcampusassetmapping.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isShiftPressed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ph.edu.batstateu.arasof.arasofcampusassetmapping.R
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.BuildingSearch
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Building
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.BuildingStatus
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Floor
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel

private const val MAX_ZOOM = 8f

private enum class LegendItem(val label: String, val symbol: String) {
    ELECTRICAL("Electrical box / electrical asset", "\u26A1"),
    SEGREGATION("Segregation station", "\uD83D\uDDD1\uFE0F"),
    GUARD("Guard house", "\uD83D\uDEE1\uFE0F"),
    WATER("Water tank", "\uD83D\uDCA7"),
    ERMITA("9 = Ermita Building combined with Hatchery & Research Building", "9");

    fun matches(b: Building): Boolean = when (this) {
        ELECTRICAL -> b.name.startsWith("Electrical box")
        SEGREGATION -> b.name.startsWith("Segregation Station")
        GUARD -> b.name.startsWith("Guard house")
        WATER -> b.name.startsWith("Water tank")
        ERMITA -> b.id == 9
    }
}

private fun clampOffset(o: Offset, s: Float, size: IntSize): Offset {
    val mx = size.width * (s - 1f) / 2f
    val my = size.height * (s - 1f) / 2f
    return Offset(o.x.coerceIn(-mx, mx), o.y.coerceIn(-my, my))
}

@Composable
fun CampusMapScreen(vm: CampusViewModel) {
    var picked by remember { mutableStateOf<Building?>(null) }
    var infoOf by remember { mutableStateOf<Building?>(null) }
    var showSettings by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf<String?>(null) }
    var legendPick by remember { mutableStateOf<LegendItem?>(null) }
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    // Search state
    var searchQuery by remember { mutableStateOf("") }
    val searchResultPair = remember(searchQuery) { BuildingSearch.search(searchQuery) }
    val searchResults = searchResultPair.first
    val searchSuggestions = searchResultPair.second

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

    fun zoomAt(focus: Offset, factor: Float) {
        val c = Offset(boxSize.width / 2f, boxSize.height / 2f)
        val ns = (scale * factor).coerceIn(1f, MAX_ZOOM)
        val rel = focus - c
        offset = clampOffset(rel - (rel - offset) * (ns / scale), ns, boxSize)
        scale = ns
    }

    fun reset() {
        scale = 1f
        offset = Offset.Zero
    }

    fun focusBuilding(b: Building) {
        searchQuery = ""
        notice = null
        if (b.isTappable) {
            if (!b.hasPlan) {
                infoOf = b
            } else if (b.floorCount == 1) {
                vm.openFloor(b, Floor.GROUND)
            } else {
                picked = b
            }
        } else {
            notice = "${b.id}. ${b.name} is " +
                    if (b.status == BuildingStatus.UNDER_CONSTRUCTION) "still under construction."
                    else "not built yet."
        }
    }

    fun buildingAt(p: Offset): Building? {
        if (boxSize.width == 0) return null
        val c = Offset(boxSize.width / 2f, boxSize.height / 2f)
        val content = c + (p - c - offset) / scale
        val k = CampusData.MAP_W / boxSize.width
        val mx = content.x * k
        val my = content.y * k
        CampusData.buildings.firstOrNull { !it.hasPlan && it.contains(mx, my) }?.let { return it }
        CampusData.buildings.firstOrNull { it.contains(mx, my) }?.let { return it }
        val tol = 12f * k / scale
        return CampusData.buildings
            .filter { it.distanceToBox(mx, my) <= tol }
            .minByOrNull { it.distanceToBox(mx, my) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top Modern Header Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            tonalElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "BatStateU ARASOF Map",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${vm.userName} · ${if (vm.isMaintenance) "Maintenance" else "Student"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                BadgedBox(badge = { if (vm.openCount() > 0) Badge { Text("${vm.openCount()}") } }) {
                    IconButton(
                        onClick = vm::openReports,
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(Icons.Default.Assessment, contentDescription = "Reports", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }

                IconButton(onClick = { showSettings = true }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                IconButton(onClick = { showLogoutDialog = true }) {
                    Icon(Icons.Default.Logout, contentDescription = "Log out", tint = MaterialTheme.colorScheme.error)
                }
            }
        }

        // Building Search Bar with Autocomplete & Suggestions
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search building, gym, canteen, clinic, lib...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            // Autocomplete Results Dropdown Popup
            if (searchQuery.isNotBlank() && (searchResults.isNotEmpty() || searchSuggestions.isNotEmpty())) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp)
                        .heightIn(max = 240.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    tonalElevation = 4.dp
                ) {
                    LazyColumn(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (searchResults.isNotEmpty()) {
                            items(searchResults, key = { it.building.id }) { sr ->
                                val b = sr.building
                                SearchResultItem(building = b, onClick = { focusBuilding(b) })
                            }
                        } else if (searchSuggestions.isNotEmpty()) {
                            item {
                                Text(
                                    "Did you mean:",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            items(searchSuggestions, key = { it.id }) { b ->
                                SearchResultItem(building = b, onClick = { focusBuilding(b) })
                            }
                        }
                    }
                }
            }
        }

        // Map Canvas Box
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                val ratio = CampusData.MAP_W / CampusData.MAP_H
                val mapW = minOf(maxWidth, maxHeight * ratio)
                val mapH = mapW / ratio

                Box(
                    Modifier
                        .size(mapW, mapH)
                        .clip(RoundedCornerShape(16.dp))
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
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onTap = { p ->
                                    val b = buildingAt(p)
                                    if (b == null) {
                                        notice = null
                                    } else if (b.isTappable) {
                                        notice = null
                                        if (!b.hasPlan) infoOf = b
                                        else if (b.floorCount == 1) vm.openFloor(b, Floor.GROUND) else picked = b
                                    } else {
                                        notice = "${b.id}. ${b.name} is " +
                                                if (b.status == BuildingStatus.UNDER_CONSTRUCTION) "still under construction."
                                                else "not built yet."
                                    }
                                },
                                onDoubleTap = { p -> if (scale < 2f) zoomAt(p, 2.5f) else reset() }
                            )
                        }
                ) {
                    Box(
                        Modifier.fillMaxSize().graphicsLayer(
                            scaleX = scale, scaleY = scale,
                            translationX = offset.x, translationY = offset.y
                        )
                    ) {
                        Image(
                            painter = painterResource(R.drawable.campus_map),
                            contentDescription = "Campus map",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )

                        picked?.let { b ->
                            Canvas(Modifier.fillMaxSize()) {
                                val k = size.width / CampusData.MAP_W
                                b.shapes.forEach { poly ->
                                    val path = Path()
                                    poly.forEachIndexed { i, (px, py) ->
                                        if (i == 0) path.moveTo(px * k, py * k) else path.lineTo(px * k, py * k)
                                    }
                                    path.close()
                                    drawPath(path, Color(0x441A237E).copy(alpha = pulseAlpha * 0.5f))
                                    drawPath(path, Color(0xFF1A237E), style = Stroke(width = 5f))
                                }
                            }
                        }

                        legendPick?.let { item ->
                            Canvas(Modifier.fillMaxSize()) {
                                val k = size.width / CampusData.MAP_W
                                val accent = Color(0xFFFF6F00)
                                CampusData.buildings.filter { item.matches(it) }.forEach { b ->
                                    b.shapes.forEach { poly ->
                                        val path = Path()
                                        poly.forEachIndexed { i, (px, py) ->
                                            if (i == 0) path.moveTo(px * k, py * k) else path.lineTo(px * k, py * k)
                                        }
                                        path.close()
                                        drawPath(path, accent.copy(alpha = 0.25f))
                                        drawPath(path, accent, style = Stroke(width = 4f))
                                    }
                                    if (!b.hasPlan) {
                                        drawCircle(
                                            accent, radius = 52f * k,
                                            center = Offset((b.x + b.w / 2f) * k, (b.y + b.h / 2f) * k),
                                            style = Stroke(width = 4f)
                                        )
                                    }
                                }
                            }
                        }

                        if (vm.isMaintenance) {
                            val unit = mapW / CampusData.MAP_W
                            CampusData.buildings.filter { it.isTappable }.forEach { b ->
                                val open = vm.openCount(b.id)
                                if (open > 0) {
                                    Box(
                                        Modifier
                                            .offset(unit * (b.x + b.w) - 9.dp, unit * b.y - 9.dp)
                                            .size(18.dp).clip(CircleShape).background(Color.Red),
                                        contentAlignment = Alignment.Center
                                    ) { Text("$open", color = Color.White, fontSize = 10.sp) }
                                }
                            }
                        }
                    }
                }
            }
        }

        notice?.let {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.errorContainer
            ) {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        // Map Control Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val mid = Offset(boxSize.width / 2f, boxSize.height / 2f)
                IconButton(onClick = { zoomAt(mid, 1f / 1.5f) }) {
                    Icon(Icons.Default.ZoomOut, contentDescription = "Zoom out")
                }
                IconButton(onClick = { zoomAt(mid, 1.5f) }) {
                    Icon(Icons.Default.ZoomIn, contentDescription = "Zoom in")
                }
                FilledTonalButton(
                    onClick = { reset() },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Reset")
                }
                Text(
                    "Gray = under construction",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Legend Filter Chips
        Row(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LegendItem.values().forEach { item ->
                val n = CampusData.buildings.count { item.matches(it) }
                FilterChip(
                    selected = legendPick == item,
                    onClick = { legendPick = if (legendPick == item) null else item; notice = null },
                    label = {
                        Text(
                            if (item == LegendItem.ERMITA) item.label else "${item.symbol} ${item.label} ($n)",
                            style = MaterialTheme.typography.labelMedium
                        )
                    },
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    }

    if (showSettings) SettingsDialog(vm) { showSettings = false }

    infoOf?.let { b ->
        AlertDialog(
            onDismissRequest = { infoOf = null },
            shape = RoundedCornerShape(24.dp),
            title = { Text(b.name, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(b.category.label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    if (b.info.isNotBlank()) Text(b.info)
                }
            },
            confirmButton = {
                Button(onClick = { infoOf = null }, shape = RoundedCornerShape(12.dp)) { Text("OK") }
            }
        )
    }

    picked?.let { b ->
        AlertDialog(
            onDismissRequest = { picked = null },
            shape = RoundedCornerShape(24.dp),
            title = { Text("${b.id}. ${b.name}", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(b.category.label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                    Text(if (b.floorCount > 1) "Which floor do you want to go to?" else "View this area:")
                    b.floors.forEach { f ->
                        FilledTonalButton(
                            onClick = { picked = null; vm.openFloor(b, f) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) { Text(f.label) }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { picked = null }) { Text("Cancel") }
            }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            shape = RoundedCornerShape(24.dp),
            title = { Text("Log out", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        vm.logout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Log out")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SearchResultItem(building: Building, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${building.id}. ${building.name}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = building.category.label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        val (statusText, statusColor) = when (building.status) {
            BuildingStatus.OPEN -> "Open" to MaterialTheme.colorScheme.primaryContainer
            BuildingStatus.UNDER_CONSTRUCTION -> "Under construction" to MaterialTheme.colorScheme.tertiaryContainer
            BuildingStatus.UNBUILT -> "Not built" to MaterialTheme.colorScheme.errorContainer
        }

        Surface(
            shape = RoundedCornerShape(6.dp),
            color = statusColor
        ) {
            Text(
                text = statusText,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}
