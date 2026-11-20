package ph.edu.batstateu.arasof.arasofcampusassetmapping.data.repository

import android.content.Context
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.IncidentDatabase
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.repository.IncidentRepository
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Incident

/**
 * SQLite-backed implementation of IncidentRepository.
 * Demonstrates Dependency Inversion Principle (DIP).
 */
class SqliteIncidentRepository(context: Context) : IncidentRepository {
    private val db = IncidentDatabase(context)

    override fun loadAll(): List<Incident> = db.loadAll()
    override fun save(incident: Incident) = db.save(incident)
    override fun update(incident: Incident) = db.save(incident)
    override fun delete(id: String) {}
    override fun loadAssetDetails(): Map<String, Any> = emptyMap()
    override fun saveAssetDetail(assetId: String, details: Map<String, Any>) {}
}
