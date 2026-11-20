package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Floor
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Incident
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.IncidentStatus
import java.util.concurrent.Executors

/**
 * Small local database (plain Android SQLite, nothing to add in Gradle) that keeps the reported
 * incidents on the phone, so they are still there after the app is closed or the phone restarts.
 * One table: incidents. Saving runs on a background thread; loading at start-up is tiny and runs once.
 */
class IncidentDatabase(context: Context) :
    SQLiteOpenHelper(context.applicationContext, "campus_assets.db", null, 2) {
    val appContext: Context = context.applicationContext

    private val writer = Executors.newSingleThreadExecutor()

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE incidents (
                id TEXT PRIMARY KEY,
                asset_id TEXT NOT NULL,
                asset_tag TEXT NOT NULL,
                asset_label TEXT NOT NULL,
                building_id INTEGER NOT NULL,
                building_name TEXT NOT NULL,
                floor TEXT NOT NULL,
                description TEXT NOT NULL,
                status TEXT NOT NULL,
                reported_by TEXT NOT NULL,
                reported_at INTEGER NOT NULL,
                updated_at INTEGER NOT NULL,
                resolved_by TEXT,
                resolved_at INTEGER,
                resolution_note TEXT,
                also_reported_by TEXT NOT NULL DEFAULT ''
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE incidents ADD COLUMN also_reported_by TEXT NOT NULL DEFAULT ''")
        }
    }

    /** All saved incidents, newest first. */
    fun loadAll(): List<Incident> {
        val list = mutableListOf<Incident>()
        readableDatabase.query("incidents", null, null, null, null, null, "reported_at DESC").use { c ->
            while (c.moveToNext()) list += c.toIncident()
        }
        return list
    }

    /** Insert a new incident or update an existing one (same id). */
    fun save(incident: Incident) {
        val v = ContentValues().apply {
            put("id", incident.id)
            put("asset_id", incident.assetId)
            put("asset_tag", incident.assetTag)
            put("asset_label", incident.assetLabel)
            put("building_id", incident.buildingId)
            put("building_name", incident.buildingName)
            put("floor", incident.floor.name)
            put("description", incident.description)
            put("status", incident.status.name)
            put("reported_by", incident.reportedBy)
            put("reported_at", incident.reportedAt)
            put("updated_at", incident.updatedAt)
            put("resolved_by", incident.resolvedBy)
            put("resolved_at", incident.resolvedAt)
            put("resolution_note", incident.resolutionNote)
            put("also_reported_by", incident.alsoReportedBy.joinToString("\n"))
        }
        writer.execute {
            writableDatabase.insertWithOnConflict("incidents", null, v, SQLiteDatabase.CONFLICT_REPLACE)
        }
    }

    private fun Cursor.str(col: String): String = getString(getColumnIndexOrThrow(col))
    private fun Cursor.strOrNull(col: String): String? =
        getColumnIndexOrThrow(col).let { if (isNull(it)) null else getString(it) }
    private fun Cursor.longOrNull(col: String): Long? =
        getColumnIndexOrThrow(col).let { if (isNull(it)) null else getLong(it) }

    private fun Cursor.toIncident() = Incident(
        id = str("id"),
        assetId = str("asset_id"),
        assetTag = str("asset_tag"),
        assetLabel = str("asset_label"),
        buildingId = getInt(getColumnIndexOrThrow("building_id")),
        buildingName = str("building_name"),
        floor = runCatching { Floor.valueOf(str("floor")) }.getOrDefault(Floor.GROUND),
        description = str("description"),
        status = runCatching { IncidentStatus.valueOf(str("status")) }.getOrDefault(IncidentStatus.OPEN),
        reportedBy = str("reported_by"),
        reportedAt = getLong(getColumnIndexOrThrow("reported_at")),
        updatedAt = getLong(getColumnIndexOrThrow("updated_at")),
        resolvedBy = strOrNull("resolved_by"),
        resolvedAt = longOrNull("resolved_at"),
        resolutionNote = strOrNull("resolution_note"),
        alsoReportedBy = str("also_reported_by").split("\n").filter { it.isNotBlank() }
    )
}