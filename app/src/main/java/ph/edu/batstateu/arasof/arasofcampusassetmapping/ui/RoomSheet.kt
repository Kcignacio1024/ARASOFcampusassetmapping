package ph.edu.batstateu.arasof.arasofcampusassetmapping.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Asset
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.AssetType
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Room
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel

/** Fire / safety assets are listed after normal equipment. */
private val safetyTypes = setOf(
    AssetType.EXIT, AssetType.FIRE_ALARM, AssetType.FIRE_LIGHT,
    AssetType.HYDRANT, AssetType.EXTINGUISHER, AssetType.SPRINKLER
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomSheet(
    room: Room,
    assets: List<Asset>,
    vm: CampusViewModel,
    onAssetClick: (Asset) -> Unit,
    onLocateOnPlan: (Asset) -> Unit,
    onDismiss: () -> Unit
) {
    val cs = MaterialTheme.colorScheme
    var searchQuery by remember { mutableStateOf("") }
    val issueCount = assets.count { vm.openIncidentFor(it.id) != null }

    val filteredAssets = assets.filter { a ->
        val q = searchQuery.trim().lowercase()
        if (q.isBlank()) true
        else a.tag.lowercase().contains(q) || a.label.lowercase().contains(q) || a.type.label.lowercase().contains(q) || (a.locationNote?.lowercase()?.contains(q) == true)
    }

    // Group filtered assets by type
    val groups = filteredAssets.groupBy { it.type }.entries
        .sortedWith(compareBy({ it.key in safetyTypes }, { it.key.label }))

    // Default expand groups if 1 group or searching
    var expandedTypes by remember(searchQuery) {
        mutableStateOf(if (searchQuery.isNotBlank() || groups.size <= 1) groups.map { it.key }.toSet() else emptySet())
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ---- header
            item {
                Column(Modifier.padding(bottom = 4.dp)) {
                    Text(room.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Pill(room.floor.label, cs.primaryContainer, cs.onPrimaryContainer)
                        Pill("${assets.size} assets", cs.secondaryContainer, cs.onSecondaryContainer)
                        if (issueCount > 0) {
                            Pill(
                                if (issueCount == 1) "1 issue" else "$issueCount issues",
                                cs.errorContainer, cs.onErrorContainer
                            )
                        }
                    }
                }
            }

            // Computer count for maintenance
            if (vm.isMaintenance) {
                item {
                    Row(
                        Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp))
                            .background(cs.tertiaryContainer.copy(alpha = 0.6f)).padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            if (room.computers > 0) "Computers: about ${room.computers}" else "No computers in this room",
                            style = MaterialTheme.typography.bodyMedium, color = cs.onTertiaryContainer
                        )
                    }
                }
            }

            // Search filter bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search by tag or asset name...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            if (assets.isEmpty()) {
                item {
                    Text(
                        "No assets recorded for this area yet.",
                        style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            } else if (filteredAssets.isEmpty()) {
                item {
                    Text(
                        "No matching assets found.",
                        style = MaterialTheme.typography.bodyMedium, color = cs.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }

            // ---- Summarized dropdown / accordion card per asset type
            groups.forEach { (type, list) ->
                val isOpen = type in expandedTypes
                val groupIssues = list.count { vm.openIncidentFor(it.id) != null }

                item(key = "group-${type.name}") {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                expandedTypes = if (isOpen) expandedTypes - type else expandedTypes + type
                            },
                        color = cs.surfaceContainerHigh,
                        tonalElevation = 1.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                Modifier.size(36.dp).clip(CircleShape).background(type.color()),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(type.symbol, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    type.label,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    if (list.size == 1) "1 item" else "${list.size} items",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = cs.onSurfaceVariant
                                )
                            }

                            if (groupIssues > 0) {
                                Pill("$groupIssues issue${if (groupIssues > 1) "s" else ""}", cs.errorContainer, cs.onErrorContainer)
                            }

                            Text(if (isOpen) "▾" else "▸", color = cs.onSurfaceVariant, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // If expanded, list individual items with tags, location notes, condition, and locate button
                if (isOpen) {
                    items(list, key = { it.id }) { a ->
                        val issue = vm.openIncidentFor(a.id) != null
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                                .clickable { onAssetClick(a) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = cs.surfaceVariant.copy(alpha = 0.5f)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        a.label,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    if (a.tag.isNotBlank()) {
                                        Text(
                                            a.tag,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = cs.primary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    if (!a.locationNote.isNullOrBlank()) {
                                        Text(
                                            "Location: ${a.locationNote}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = cs.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    if (issue) {
                                        Pill("Issue", cs.errorContainer, cs.onErrorContainer)
                                    } else {
                                        Pill(a.condition.label, cs.primaryContainer, cs.onPrimaryContainer)
                                    }

                                    OutlinedButton(
                                        onClick = { onLocateOnPlan(a) },
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.height(28.dp)
                                    ) {
                                        Text("Locate", fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Pill(text: String, background: Color, content: Color) {
    Text(
        text,
        modifier = Modifier.clip(RoundedCornerShape(50)).background(background)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        color = content, fontSize = 11.sp, fontWeight = FontWeight.Medium, maxLines = 1
    )
}
