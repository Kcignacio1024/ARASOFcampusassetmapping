package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 18 (Green open court, multipurpose court): ground floor only.
 * Numbers are PIXELS of the plan picture (1920 x 1080). The frame is the court itself.
 * The only assets are the 2 basketball shooting boards (north and south end), each with an icon.
 */
object Building18Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(18, Floor.GROUND, "B18-1", 433.0f, 225.0f, 366.0f, 587.0f).apply {
            room("court", "Multipurpose Court (Green Building No. 18)", 433.0f, 225.0f, 799.0f, 812.0f, computers = 0)
            asset(AssetType.BASKETBALL_BOARD, 616.0f, 252.0f, "court", "Basketball shooting board (north) · Multipurpose Court")
            asset(AssetType.BASKETBALL_BOARD, 616.0f, 785.0f, "court", "Basketball shooting board (south) · Multipurpose Court")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId == 18 && floor == Floor.GROUND) ground else null
}