package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Helper for writing a real floor plan. Give it the frame (x0, y0, width, height) of the drawing
 * in PIXELS, then add rooms / zones / assets using pixel coordinates read off the drawing.
 * Use it for every new building.
 */
class PlanBuilder(
    private val buildingId: Int,
    private val floor: Floor,
    private val prefix: String,
    private val x0: Float, private val y0: Float, private val pw: Float, private val ph: Float
) {
    private val rooms = mutableListOf<Room>()
    private val zones = mutableListOf<Zone>()
    private val assets = mutableListOf<Asset>()
    private var n = 0

    private fun fx(px: Float) = (px - x0) / pw
    private fun fy(py: Float) = (py - y0) / ph

    fun room(
        key: String, name: String, l: Float, t: Float, r: Float, b: Float,
        computers: Int = 0, outline: List<Pair<Float, Float>> = emptyList()
    ) {
        rooms += Room(
            "$prefix-$key", buildingId, floor, name, fx(l), fy(t), (r - l) / pw, (b - t) / ph, computers,
            outline.map { fx(it.first) to fy(it.second) }
        )
    }

    fun zone(
        name: String, kind: ZoneKind, l: Float, t: Float, r: Float, b: Float,
        outline: List<Pair<Float, Float>> = emptyList()
    ) {
        zones += Zone(name, kind, fx(l), fy(t), (r - l) / pw, (b - t) / ph, outline.map { fx(it.first) to fy(it.second) })
    }

    fun asset(type: AssetType, px: Float, py: Float, roomKey: String?, detail: String = "") {
        val roomName = rooms.firstOrNull { it.id == "$prefix-$roomKey" }?.name
        val label = detail.ifBlank { "${type.label} · $roomName" }
        n++
        assets += Asset("$prefix-A$n", buildingId, floor, type, fx(px), fy(py), label, roomKey?.let { "$prefix-$it" })
    }

    fun build(note: String? = null) = FloorData(rooms.toList(), assets.toList(), zones.toList(), pw / ph, note)
}