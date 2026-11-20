package ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.repository

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Incident

/**
 * Repository interface for incident persistence.
 * Demonstrates Dependency Inversion Principle (DIP) and Interface Segregation (ISP).
 */
interface IncidentRepository {
    fun loadAll(): List<Incident>
    fun save(incident: Incident)
    fun update(incident: Incident)
    fun delete(id: String)
    fun loadAssetDetails(): Map<String, Any>
    fun saveAssetDetail(assetId: String, details: Map<String, Any>)
}
