package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 14 (Student Council Building, pink building): ground floor only.
 * Numbers are PIXELS of the plan picture (1920 x 1080). The frame is the two rooms plus the hallway.
 *
 *  - 2 rooms (north and south), each with: 1 air conditioner (on the room's outer side: north room =
 *    north wall, south room = south wall), 1 PC beside the door, and 2 Wi-Fi access points.
 *  - 1 fire extinguisher in the hallway to Calatagan Building, shared by the two rooms.
 * Asset positions are estimates: edit freely.
 */
object Building14Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(14, Floor.GROUND, "B14-1", 372.0f, 301.0f, 278.0f, 479.0f).apply {
            room("north", "Student Council Room (north)", 388.0f, 317.0f, 551.0f, 531.0f, computers = 1)
            room("south", "Student Council Room (south)", 388.0f, 547.0f, 551.0f, 765.0f, computers = 1)
            room("hall", "Hallway to Calatagan Building", 568.0f, 305.0f, 640.0f, 765.0f, computers = 0)

            // north room
            asset(AssetType.AIRCON, 469.0f, 335.0f, "north", "Air conditioner · Student Council Room (north)")
            asset(AssetType.COMPUTER, 535.0f, 498.0f, "north", "PC (beside door) · Student Council Room (north)")
            asset(AssetType.WIFI, 512.0f, 385.0f, "north", "Wi-Fi access point 2 · Student Council Room (north)")

            // south room
            asset(AssetType.AIRCON, 469.0f, 748.0f, "south", "Air conditioner · Student Council Room (south)")
            asset(AssetType.COMPUTER, 535.0f, 582.0f, "south", "PC (beside door) · Student Council Room (south)")
            asset(AssetType.WIFI, 512.0f, 625.0f, "south", "Wi-Fi access point 2 · Student Council Room (south)")

            // hallway (shared by both rooms)
            asset(AssetType.EXTINGUISHER, 604.0f, 540.0f, "hall", "Fire extinguisher · Hallway to Calatagan Building")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId == 14 && floor == Floor.GROUND) ground else null
}