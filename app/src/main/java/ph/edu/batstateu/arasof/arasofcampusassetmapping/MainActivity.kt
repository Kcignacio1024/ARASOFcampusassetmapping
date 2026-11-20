package ph.edu.batstateu.arasof.arasofcampusassetmapping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.IncidentDatabase
import ph.edu.batstateu.arasof.arasofcampusassetmapping.ui.*
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel
import ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.Screen

class MainActivity : ComponentActivity() {
    private val vm: CampusViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        vm.loadSettings(
            showIcons = prefs.getBoolean("show_asset_icons_summary", false),
            roomSp = prefs.getFloat("room_text_sp", 15f)
        ) { showIcons, roomSp ->
            prefs.edit()
                .putBoolean("show_asset_icons_summary", showIcons)
                .putFloat("room_text_sp", roomSp)
                .apply()
        }
        vm.attachDatabase(IncidentDatabase(this)) // saved incidents come back after the app is closed
        setContent {
            // Swap MaterialTheme for the generated theme in ui/theme if you prefer it
            MaterialTheme {
                BackHandler(enabled = vm.screen == Screen.FLOOR || vm.screen == Screen.REPORTS) { vm.back() }
                Scaffold { padding ->
                    val m = Modifier.padding(padding)
                    androidx.compose.foundation.layout.Box(m) {
                        when (vm.screen) {
                            Screen.LOGIN -> LoginScreen(vm)
                            Screen.MAP -> CampusMapScreen(vm)
                            Screen.FLOOR -> FloorPlanScreen(vm)
                            Screen.REPORTS -> ReportsScreen(vm)
                        }
                    }
                }
            }
        }
    }
}
