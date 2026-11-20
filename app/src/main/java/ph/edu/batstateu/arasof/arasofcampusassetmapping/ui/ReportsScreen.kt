package ph.edu.batstateu.arasof.arasofcampusassetmapping.ui

import android.content.Intent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Incident
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.IncidentStatus
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun fmt(t: Long) = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date(t))

@Composable
fun ReportsScreen(vm: CampusViewModel) {
    var filter by remember { mutableStateOf(IncidentStatus.OPEN) }
    var selectedBuildingId by remember { mutableStateOf<Int?>(null) }
    var resolving by remember { mutableStateOf<Incident?>(null) }
    var note by remember { mutableStateOf("") }
    val context = LocalContext.current

    val list = vm.incidents.filter {
        it.status == filter && (selectedBuildingId == null || it.buildingId == selectedBuildingId)
    }.sortedByDescending { it.reportedAt }

    val buildingsWithIncidents = CampusData.buildings.filter { b -> vm.incidents.any { it.buildingId == b.id } }

    fun exportCsv() {
        val sb = StringBuilder()
        sb.append("ID,Asset Tag,Asset Label,Building,Floor,Description,Status,Reported By,Reported At,Resolved By,Resolved At,Resolution Note\n")
        for (inc in vm.incidents) {
            val resolvedAtStr = inc.resolvedAt?.let { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(it)) } ?: ""
            val reportedAtStr = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(inc.reportedAt))
            sb.append("\"${inc.id}\",\"${inc.assetTag}\",\"${inc.assetLabel.replace("\"", "\"\"")}\",\"${inc.buildingName}\",\"${inc.floor.label}\",\"${inc.description.replace("\"", "\"\"")}\",\"${inc.status}\",\"${inc.reportedBy}\",\"$reportedAtStr\",\"${inc.resolvedBy ?: ""}\",\"$resolvedAtStr\",\"${(inc.resolutionNote ?: "").replace("\"", "\"\"")}\"\n")
        }
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, sb.toString())
            type = "text/csv"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Export Reports CSV")
        try {
            context.startActivity(shareIntent)
        } catch (_: Exception) {
            // handle error
        }
    }

    Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = vm::back) { Text("← Map") }
            Text("Reports", style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
            FilledTonalButton(onClick = { exportCsv() }, shape = RoundedCornerShape(10.dp)) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(4.dp))
                Text("Export CSV")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(filter == IncidentStatus.OPEN, { filter = IncidentStatus.OPEN }, { Text("Open (${vm.openCount()})") })
            FilterChip(filter == IncidentStatus.RESOLVED, { filter = IncidentStatus.RESOLVED }, { Text("Resolved") })
        }

        // Building filter chips
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            FilterChip(
                selected = selectedBuildingId == null,
                onClick = { selectedBuildingId = null },
                label = { Text("All Buildings") }
            )
            buildingsWithIncidents.forEach { b ->
                FilterChip(
                    selected = selectedBuildingId == b.id,
                    onClick = { selectedBuildingId = if (selectedBuildingId == b.id) null else b.id },
                    label = { Text("${b.id}. ${b.name}") }
                )
            }
        }

        if (list.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nothing here yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.weight(1f)) {
                items(list, key = { it.id }) { inc ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(inc.assetLabel, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                                if (inc.assetTag.isNotBlank()) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        Text(inc.assetTag, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                    }
                                }
                            }
                            Text("${inc.buildingName} · ${inc.floor.label}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                            Text(inc.description, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(2.dp))
                            Text("Reported by ${inc.reportedBy} · ${fmt(inc.reportedAt)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            if (inc.alsoReportedBy.isNotEmpty()) {
                                Text("Also reported by ${inc.alsoReportedBy.joinToString(", ")} (${inc.reportCount} people)", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            if (inc.status == IncidentStatus.RESOLVED) {
                                Text("Fixed by ${inc.resolvedBy} · ${fmt(inc.resolvedAt ?: 0)}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                                inc.resolutionNote?.let { Text("Note: $it", style = MaterialTheme.typography.bodySmall) }
                            } else if (vm.isMaintenance) {
                                Spacer(Modifier.height(6.dp))
                                Button(onClick = { note = ""; resolving = inc }) { Text("Mark as fixed") }
                            } else {
                                Text("Waiting for maintenance", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }

    resolving?.let { inc ->
        AlertDialog(
            onDismissRequest = { resolving = null },
            title = { Text("Mark as fixed?") },
            text = {
                OutlinedTextField(note, { note = it }, label = { Text("Fix note (optional)") }, modifier = Modifier.fillMaxWidth())
            },
            confirmButton = { Button(onClick = { vm.resolve(inc, note); resolving = null }) { Text("Confirm") } },
            dismissButton = { TextButton(onClick = { resolving = null }) { Text("Cancel") } }
        )
    }
}