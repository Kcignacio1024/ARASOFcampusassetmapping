package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 17 (Maintenance Building): ground floor only, one open room with a single
 * door in the south wall (west end). The building stores the tools, spare parts, mechanical equipment,
 * safety gear and supplies used to keep the campus running.
 * Numbers are PIXELS of the plan picture (1920 x 1080). The frame is the outer wall of the building.
 *
 *  - NO air conditioner (none exists here).
 *  - Fire extinguisher (1): inside, next to the door, with an icon. Position is an estimate: edit freely.
 */
object Building17Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(17, Floor.GROUND, "B17-1", 319.0f, 209.0f, 424.0f, 332.0f).apply {
            room("main", "Maintenance Building", 348.0f, 236.0f, 714.0f, 514.0f, computers = 0)
            zone("Door", ZoneKind.PLAIN, 355.0f, 514.0f, 421.0f, 541.0f)
            asset(AssetType.EXTINGUISHER, 385.0f, 485.0f, "main", "Fire extinguisher · Maintenance Building (next to the door)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId == 17 && floor == Floor.GROUND) ground else null
}