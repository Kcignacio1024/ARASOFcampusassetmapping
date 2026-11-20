package ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.IncidentDatabase
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.repository.SqliteIncidentRepository
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.service.IncidentService
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.user.AppUser
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.user.MaintenanceStaff
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.user.Student
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

enum class Screen { LOGIN, MAP, FLOOR, REPORTS }

/**
 * ViewModel managing UI state and delegating business rules to IncidentService.
 * Demonstrates SRP, DIP, and Polymorphism via AppUser.
 */
class CampusViewModel : ViewModel() {

    init {
        ph.edu.batstateu.arasof.arasofcampusassetmapping.data.AssetTagGenerator.populateTags()
    }

    companion object {
        const val MAINTENANCE_PIN = "1234"
    }

    var screen by mutableStateOf(Screen.LOGIN); private set
    var currentUser by mutableStateOf<AppUser?>(null); private set
    var selectedBuilding by mutableStateOf<Building?>(null); private set
    var selectedFloor by mutableStateOf(Floor.GROUND); private set

    val incidents = mutableStateListOf<Incident>()

    private var incidentService: IncidentService? = null

    fun attachDatabase(db: IncidentDatabase) {
        val repo = SqliteIncidentRepository(db.appContext)
        incidentService = IncidentService(repo)
        incidents.clear()
        incidents.addAll(incidentService?.loadIncidents() ?: db.loadAll())
    }

    var showAssetIcons by mutableStateOf(true); private set
    var roomTextSp by mutableFloatStateOf(15f); private set
    private var saveSettings: ((Boolean, Float) -> Unit)? = null

    fun loadSettings(showIcons: Boolean, roomSp: Float, onSave: (Boolean, Float) -> Unit) {
        showAssetIcons = showIcons; roomTextSp = roomSp; saveSettings = onSave
    }
    fun updateShowAssetIcons(value: Boolean) { showAssetIcons = value; saveSettings?.invoke(value, roomTextSp) }
    fun updateRoomTextSp(value: Float) { roomTextSp = value; saveSettings?.invoke(showAssetIcons, value) }

    val isMaintenance get() = currentUser is MaintenanceStaff
    val userName get() = currentUser?.name ?: ""
    val role get() = if (isMaintenance) Role.MAINTENANCE else Role.STUDENT

    fun login(name: String, role: Role, pin: String): String? {
        if (name.isBlank()) return "Please enter your name"
        if (role == Role.MAINTENANCE && pin != MAINTENANCE_PIN) return "Incorrect maintenance PIN"
        
        currentUser = if (role == Role.MAINTENANCE) {
            MaintenanceStaff(name.trim())
        } else {
            Student(name.trim())
        }
        screen = Screen.MAP
        return null
    }

    fun logout() {
        currentUser = null; selectedBuilding = null
        screen = Screen.LOGIN
    }

    fun openFloor(b: Building, f: Floor) {
        selectedBuilding = b; selectedFloor = f; screen = Screen.FLOOR
    }

    fun switchFloor(f: Floor) { selectedFloor = f }
    fun openReports() { screen = Screen.REPORTS }

    fun back() {
        when (screen) {
            Screen.FLOOR, Screen.REPORTS -> screen = Screen.MAP
            else -> Unit
        }
    }

    fun openIncidentFor(assetId: String): Incident? =
        incidentService?.openIncidentFor(incidents, assetId) ?: incidents.firstOrNull { it.assetId == assetId && it.status == IncidentStatus.OPEN }

    fun openCount(buildingId: Int? = null): Int =
        incidents.count { it.status == IncidentStatus.OPEN && (buildingId == null || it.buildingId == buildingId) }

    fun reportIssue(asset: Asset, description: String) {
        val user = currentUser ?: return
        incidentService?.reportIssue(incidents, asset, description, user)
    }

    fun hasReported(incident: Incident): Boolean {
        val name = userName
        return incident.reportedBy.equals(name, ignoreCase = true) ||
                incident.alsoReportedBy.any { it.equals(name, ignoreCase = true) }
    }

    fun confirmIssue(incident: Incident) {
        val name = userName
        if (incident.status != IncidentStatus.OPEN || name.isBlank() || hasReported(incident)) return
        val i = incidents.indexOfFirst { it.id == incident.id }
        if (i < 0) return
        val updated = incidents[i].copy(
            alsoReportedBy = incidents[i].alsoReportedBy + name,
            updatedAt = System.currentTimeMillis()
        )
        incidents[i] = updated
    }

    fun resolve(incident: Incident, note: String) {
        val user = currentUser ?: return
        incidentService?.resolve(incidents, incident, note, user)
    }
}
