package ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.service

import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.repository.IncidentRepository
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.user.AppUser
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Asset
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Incident
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.IncidentStatus
import java.util.UUID

/**
 * Encapsulates incident business rules (one open report per asset, resolution permission, joining reports).
 * Demonstrates Single Responsibility Principle (SRP).
 */
class IncidentService(private val repository: IncidentRepository) {

    fun loadIncidents(): List<Incident> = repository.loadAll()

    fun openIncidentFor(incidents: List<Incident>, assetId: String): Incident? =
        incidents.firstOrNull { it.assetId == assetId && it.status == IncidentStatus.OPEN }

    fun reportIssue(incidents: MutableList<Incident>, asset: Asset, description: String, user: AppUser): String? {
        val existing = openIncidentFor(incidents, asset.id)
        if (existing != null) {
            if (existing.reportedBy == user.name || existing.alsoReportedBy.contains(user.name)) {
                return "You have already reported this issue."
            }
            val updatedAlso = existing.alsoReportedBy + user.name
            val updated = existing.copy(alsoReportedBy = updatedAlso, updatedAt = System.currentTimeMillis())
            repository.save(updated)
            val idx = incidents.indexOfFirst { it.id == existing.id }
            if (idx >= 0) incidents[idx] = updated
            return null
        }

        val b = ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData.buildings.first { it.id == asset.buildingId }
        val newIncident = Incident(
            id = UUID.randomUUID().toString(),
            assetId = asset.id,
            assetTag = asset.tag,
            assetLabel = asset.label,
            buildingId = b.id,
            buildingName = b.name,
            floor = asset.floor,
            description = description.ifBlank { "No details provided" },
            reportedBy = user.name,
            reportedAt = System.currentTimeMillis()
        )
        repository.save(newIncident)
        incidents.add(0, newIncident)
        return null
    }

    fun resolve(incidents: MutableList<Incident>, incident: Incident, note: String, user: AppUser): String? {
        if (!user.canResolveIncident()) {
            return "Only maintenance staff can resolve incidents."
        }
        val i = incidents.indexOfFirst { it.id == incident.id }
        if (i < 0) return "Incident not found."

        val updated = incidents[i].copy(
            status = IncidentStatus.RESOLVED,
            resolvedBy = user.name,
            resolvedAt = System.currentTimeMillis(),
            resolutionNote = note.ifBlank { null },
            updatedAt = System.currentTimeMillis()
        )
        repository.save(updated)
        incidents[i] = updated
        return null
    }
}
