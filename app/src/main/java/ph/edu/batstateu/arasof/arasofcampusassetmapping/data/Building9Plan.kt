package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 9 (Ermita Building, orange building): ground floor and 2nd floor.
 * Numbers are PIXELS of the plan picture (1920 x 1080). The ground floor is the left drawing and the
 * 2nd floor is the right drawing, each with its own frame. Assets are estimates: edit freely.
 *
 *  - Fire extinguishers (icon): registrar, both SFAO rooms and both 2nd-floor offices, 1 each.
 *  - Wi-Fi (icon): registrar and the SFAO east room.
 *  - NO icon (icon = false), but listed when the room is tapped: air conditioners (registrar, both
 *    SFAO rooms, both 2nd-floor offices), computers (registrar 4, SFAO west 2, SFAO east 1) and the
 *    SFAO sports balls (5 basketballs, 5 volleyballs).
 *  - The two fishponds are drawn as plain zones (they are not assets).
 */
object Building9Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(9, Floor.GROUND, "B9-1", 548.0f, 315.0f, 525.0f, 610.0f).apply {
            room("registrar", "Registrar (1st floor)", 641.0f, 417.0f, 725.0f, 592.0f, computers = 4)
            room("empty", "Empty Room", 737.0f, 417.0f, 825.0f, 592.0f, computers = 0)
            room("unknown", "Unknown Room", 915.0f, 341.0f, 1052.0f, 405.0f, computers = 0)
            room("stock_n", "Stock Room (north)", 915.0f, 417.0f, 1052.0f, 598.0f, computers = 0)
            room("stock_s", "Stock Room (south)", 915.0f, 610.0f, 1052.0f, 791.0f, computers = 0)
            room("sfao_w", "SFAO (Sports Office) west", 843.0f, 803.0f, 937.0f, 903.0f, computers = 2)
            room("sfao_e", "SFAO (Sports Office) east", 949.0f, 803.0f, 1052.0f, 903.0f, computers = 1)
            zone("Walkway", ZoneKind.CORRIDOR, 560.0f, 440.0f, 628.0f, 560.0f)
            zone("Walkway", ZoneKind.CORRIDOR, 838.0f, 520.0f, 903.0f, 795.0f)
            zone("Stairs to second floor", ZoneKind.STAIRS, 657.0f, 618.0f, 813.0f, 642.0f)
            zone("Fishpond 1", ZoneKind.PLAIN, 649.0f, 335.0f, 712.0f, 392.0f)
            zone("Fishpond 2", ZoneKind.PLAIN, 744.0f, 335.0f, 807.0f, 392.0f)
            asset(AssetType.AIRCON, 683.0f, 430.0f, "registrar", "", icon = false)
            asset(AssetType.WIFI, 683.0f, 470.0f, "registrar", "")
            asset(AssetType.EXTINGUISHER, 683.0f, 575.0f, "registrar", "")
            asset(AssetType.COMPUTER, 660.0f, 500.0f, "registrar", "Computer 1 · Registrar", icon = false)
            asset(AssetType.COMPUTER, 705.0f, 500.0f, "registrar", "Computer 2 · Registrar", icon = false)
            asset(AssetType.COMPUTER, 660.0f, 540.0f, "registrar", "Computer 3 · Registrar", icon = false)
            asset(AssetType.COMPUTER, 705.0f, 540.0f, "registrar", "Computer 4 · Registrar", icon = false)
            asset(AssetType.AIRCON, 890.0f, 815.0f, "sfao_w", "", icon = false)
            asset(AssetType.EXTINGUISHER, 890.0f, 890.0f, "sfao_w", "")
            asset(AssetType.COMPUTER, 858.0f, 850.0f, "sfao_w", "Computer 1 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.COMPUTER, 858.0f, 880.0f, "sfao_w", "Computer 2 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.BASKETBALL, 862.0f, 835.0f, "sfao_w", "Basketball 1 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.BASKETBALL, 876.0f, 835.0f, "sfao_w", "Basketball 2 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.BASKETBALL, 890.0f, 835.0f, "sfao_w", "Basketball 3 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.BASKETBALL, 904.0f, 835.0f, "sfao_w", "Basketball 4 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.BASKETBALL, 918.0f, 835.0f, "sfao_w", "Basketball 5 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.VOLLEYBALL, 862.0f, 860.0f, "sfao_w", "Volleyball 1 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.VOLLEYBALL, 876.0f, 860.0f, "sfao_w", "Volleyball 2 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.VOLLEYBALL, 890.0f, 860.0f, "sfao_w", "Volleyball 3 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.VOLLEYBALL, 904.0f, 860.0f, "sfao_w", "Volleyball 4 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.VOLLEYBALL, 918.0f, 860.0f, "sfao_w", "Volleyball 5 · SFAO (Sports Office) west", icon = false)
            asset(AssetType.AIRCON, 1000.0f, 815.0f, "sfao_e", "", icon = false)
            asset(AssetType.WIFI, 1030.0f, 850.0f, "sfao_e", "")
            asset(AssetType.EXTINGUISHER, 975.0f, 890.0f, "sfao_e", "")
            asset(AssetType.COMPUTER, 1000.0f, 812.0f, "sfao_e", "Computer 1 (north side) · SFAO (Sports Office) east", icon = false)
        }.build()
    }

    private val second: FloorData by lazy {
        PlanBuilder(9, Floor.SECOND, "B9-2", 1305.0f, 405.0f, 225.0f, 265.0f).apply {
            room("office_w", "Office (2nd floor, west)", 1327.0f, 426.0f, 1417.0f, 568.0f, computers = 0)
            room("office_e", "Office (2nd floor, east)", 1423.0f, 426.0f, 1510.0f, 568.0f, computers = 0)
            zone("Hallway", ZoneKind.CORRIDOR, 1327.0f, 580.0f, 1510.0f, 600.0f)
            zone("Stairs to first floor", ZoneKind.STAIRS, 1351.0f, 628.0f, 1490.0f, 648.0f)
            asset(AssetType.AIRCON, 1371.0f, 440.0f, "office_w", "", icon = false)
            asset(AssetType.EXTINGUISHER, 1371.0f, 550.0f, "office_w", "")
            asset(AssetType.AIRCON, 1467.0f, 440.0f, "office_e", "", icon = false)
            asset(AssetType.EXTINGUISHER, 1467.0f, 550.0f, "office_e", "")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 9) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            else -> null
        }
    }
}