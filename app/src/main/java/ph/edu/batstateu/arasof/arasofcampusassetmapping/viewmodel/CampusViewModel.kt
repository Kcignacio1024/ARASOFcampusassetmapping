package ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.IncidentDatabase
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

enum class Screen { LOGIN, MAP, FLOOR, REPORTS }

class CampusViewModel : ViewModel() {

    init {
        ph.edu.batstateu.arasof.arasofcampusassetmapping.data.AssetTagGenerator.populateTags()
    }

    companion object {
        // DEMO ONLY. Replace with real authentication (e.g. Firebase Auth + a "maintenance" claim).
        const val MAINTENANCE_PIN = "1234"
    }

    var screen by mutableStateOf(Screen.LOGIN); private set
    var role by mutableStateOf(Role.STUDENT); private set
    var userName by mutableStateOf(""); private set
    var selectedBuilding by mutableStateOf<Building?>(null); private set
    var selectedFloor by mutableStateOf(Floor.GROUND); private set

    val incidents = mutableStateListOf<Incident>()

    // ---- local database: reported incidents are saved on the phone and reloaded at start-up ----
    private var database: IncidentDatabase? = null

    /** Called once from MainActivity. Loads the saved incidents; later calls (screen rotation) do nothing. */
    fun attachDatabase(db: IncidentDatabase) {
        if (database != null) return
        database = db
        incidents.clear()
        incidents.addAll(db.loadAll())
    }

    // ---- settings (saved on the phone, see MainActivity) ----
    /** true = show the asset icons (logos) on the floor plans, false = no icons */
    var showAssetIcons by mutableStateOf(true); private set
    /** size of the room names on the floor plans, in sp */
    var roomTextSp by mutableFloatStateOf(15f); private set
    private var saveSettings: ((Boolean, Float) -> Unit)? = null

    fun loadSettings(showIcons: Boolean, roomSp: Float, onSave: (Boolean, Float) -> Unit) {
        showAssetIcons = showIcons; roomTextSp = roomSp; saveSettings = onSave
    }
    fun updateShowAssetIcons(value: Boolean) { showAssetIcons = value; saveSettings?.invoke(value, roomTextSp) }
    fun updateRoomTextSp(value: Float) { roomTextSp = value; saveSettings?.invoke(showAssetIcons, value) }

    val isMaintenance get() = role == Role.MAINTENANCE

    /** @return error message, or null on success */
    fun login(name: String, role: Role, pin: String): String? {
        if (name.isBlank()) return "Please enter your name"
        if (role == Role.MAINTENANCE && pin != MAINTENANCE_PIN) return "Incorrect maintenance PIN"
        this.userName = name.trim()
        this.role = role
        screen = Screen.MAP
        return null
    }

    fun logout() {
        userName = ""; role = Role.STUDENT; selectedBuilding = null
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
        incidents.firstOrNull { it.assetId == assetId && it.status == IncidentStatus.OPEN }

    fun openCount(buildingId: Int? = null): Int =
        incidents.count { it.status == IncidentStatus.OPEN && (buildingId == null || it.buildingId == buildingId) }

    /** Students (and maintenance) can report a problem with an asset. */
    fun reportIssue(asset: Asset, description: String) {
        // one open report per asset: a second reporter (or a double tap) joins the existing report instead
        openIncidentFor(asset.id)?.let { confirmIssue(it); return }
        val b = CampusData.buildings.first { it.id == asset.buildingId }
        val incident = Incident(
            id = java.util.UUID.randomUUID().toString(),
            assetId = asset.id,
            assetTag = asset.tag,
            assetLabel = asset.label,
            buildingId = b.id, buildingName = b.name, floor = asset.floor,
            description = description.ifBlank { "No details provided" },
            reportedBy = userName, reportedAt = System.currentTimeMillis()
        )
        incidents.add(incident)
        database?.save(incident)
    }

    /** True if the logged-in user is already counted on this report (first reporter or added later). */
    fun hasReported(incident: Incident): Boolean =
        incident.reportedBy.equals(userName, ignoreCase = true) ||
                incident.alsoReportedBy.any { it.equals(userName, ignoreCase = true) }

    /** "I have this problem too": adds the user to the existing open report (no duplicate report). */
    fun confirmIssue(incident: Incident) {
        if (incident.status != IncidentStatus.OPEN || userName.isBlank() || hasReported(incident)) return
        val i = incidents.indexOfFirst { it.id == incident.id }
        if (i < 0) return
        val updated = incidents[i].copy(
            alsoReportedBy = incidents[i].alsoReportedBy + userName,
            updatedAt = System.currentTimeMillis()
        )
        incidents[i] = updated
        database?.save(updated)
    }

    /** Only maintenance may mark an incident as fixed. */
    fun resolve(incident: Incident, note: String) {
        if (!isMaintenance) return
        val i = incidents.indexOfFirst { it.id == incident.id }
        if (i < 0) return
        val now = System.currentTimeMillis()
        val updated = incidents[i].copy(
            status = IncidentStatus.RESOLVED, resolvedBy = userName,
            resolvedAt = now, updatedAt = now, resolutionNote = note.ifBlank { null }
        )
        incidents[i] = updated
        database?.save(updated)
    }
}