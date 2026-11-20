package ph.edu.batstateu.arasof.arasofcampusassetmapping.data.repository

import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.repository.IncidentRepository
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Incident

class InMemoryIncidentRepository : IncidentRepository {
    private val incidents = mutableListOf<Incident>()
    private val details = mutableMapOf<String, Any>()

    override fun loadAll(): List<Incident> = incidents.toList()
    override fun save(incident: Incident) {
        incidents.removeIf { it.id == incident.id }
        incidents.add(incident)
    }
    override fun update(incident: Incident) {
        save(incident)
    }
    override fun delete(id: String) {
        incidents.removeIf { it.id == id }
    }
    override fun loadAssetDetails(): Map<String, Any> = details.toMap()
    override fun saveAssetDetail(assetId: String, detailsMap: Map<String, Any>) {
        details[assetId] = detailsMap
    }
}
