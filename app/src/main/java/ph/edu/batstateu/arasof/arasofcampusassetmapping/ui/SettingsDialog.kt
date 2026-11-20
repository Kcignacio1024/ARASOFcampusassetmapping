package ph.edu.batstateu.arasof.arasofcampusassetmapping.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel

/** Settings for the whole system. They are saved on the phone and used on every floor plan. */
@Composable
fun SettingsDialog(vm: CampusViewModel, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Settings") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Asset icons (logos)", fontWeight = FontWeight.Medium)
                        Text(
                            if (vm.showAssetIcons) "On: asset icons are shown on the floor plans."
                            else "Off: no icons. Tap a room to see its assets.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Switch(checked = vm.showAssetIcons, onCheckedChange = vm::updateShowAssetIcons)
                }
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Room name size", fontWeight = FontWeight.Medium)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Medium" to 12f, "Large" to 15f, "Extra large" to 19f).forEach { (label, sp) ->
                            FilterChip(
                                selected = vm.roomTextSp == sp,
                                onClick = { vm.updateRoomTextSp(sp) },
                                label = { Text(label) }
                            )
                        }
                    }
                    Text("Preview: Crime Laboratory", fontSize = vm.roomTextSp.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Done") } }
    )
}
