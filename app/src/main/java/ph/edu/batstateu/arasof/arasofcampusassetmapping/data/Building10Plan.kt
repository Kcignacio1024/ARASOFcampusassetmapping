package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Building 10 (Villadolid Ground Powerhouse): ground floor only, and it is just a plain box,
 * the same as on the campus map. No rooms and no assets are shown.
 */
object Building10Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(10, Floor.GROUND, "B10-1", 0f, 0f, 100f, 100f).apply {
            zone("Villadolid Ground Powerhouse", ZoneKind.PLAIN, 0f, 0f, 100f, 100f)
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId == 10 && floor == Floor.GROUND) ground else null
}
