package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of the pink building complex, ground floor only. The picture (1920 x 1080) shows three
 * campus buildings connected together, so ONE plan is shown when you tap building 23, 24 or 25:
 *  - 23 Roxas Ground Facade, 24 RGO Commercial Rental Stall (the shop) and 25 Entrance-Exit (Guardhouse).
 * Numbers are PIXELS of the plan picture. Assets are estimates: edit freely.
 *
 *  - ATMs (2) and ID scanners (6): in the entrance hall, with icons.
 *  - Air conditioners (2): in the shop, no icon, listed when the room is tapped.
 *
 * All assets belong to building 23, so a problem reported from any of the three is listed under 23.
 */
object Building23Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(23, Floor.GROUND, "B23-1", 85.0f, 340.0f, 1530.0f, 315.0f).apply {
            room("hall", "Entrance Hall", 740.0f, 410.0f, 1022.0f, 628.0f, computers = 0)
            room("guard_s", "Guard House (hall)", 988.0f, 587.0f, 1022.0f, 627.0f, computers = 0)
            room("shop", "Shop (RGO Commercial Rental Stall)", 1148.0f, 422.0f, 1240.0f, 508.0f, computers = 0)
            room("entrance", "Entrance Gate", 1292.0f, 467.0f, 1403.0f, 525.0f, computers = 0)
            room("guard", "Guard House (gate)", 1414.0f, 468.0f, 1470.0f, 523.0f, computers = 0)
            room("exit", "Exit Gate", 1482.0f, 467.0f, 1594.0f, 527.0f, computers = 0)
            zone("Ramp", ZoneKind.RAMP, 129.0f, 452.0f, 404.0f, 528.0f)
            zone("Entrance / Exit Hallway", ZoneKind.CORRIDOR, 186.0f, 540.0f, 720.0f, 625.0f)
            zone("Ramp", ZoneKind.RAMP, 982.0f, 349.0f, 1083.0f, 390.0f)
            zone("Entrance / Exit (north)", ZoneKind.PLAIN, 802.0f, 357.0f, 955.0f, 390.0f)
            zone("Stairs", ZoneKind.STAIRS, 806.0f, 391.0f, 850.0f, 408.0f)
            zone("Stairs", ZoneKind.STAIRS, 851.0f, 391.0f, 895.0f, 408.0f)
            zone("Stairs", ZoneKind.STAIRS, 897.0f, 391.0f, 941.0f, 408.0f)
            zone("Entrance / Exit (south)", ZoneKind.PLAIN, 831.0f, 630.0f, 936.0f, 650.0f)
            zone("Stairs", ZoneKind.STAIRS, 825.0f, 611.0f, 869.0f, 628.0f)
            zone("Stairs", ZoneKind.STAIRS, 901.0f, 611.0f, 945.0f, 628.0f)
            asset(AssetType.ATM, 757.0f, 525.0f, "hall", "ATM 1 · Entrance Hall")
            asset(AssetType.ATM, 784.0f, 525.0f, "hall", "ATM 2 · Entrance Hall")
            asset(AssetType.ID_SCANNER, 825.0f, 490.0f, "hall", "ID scanner 1 · Entrance Hall")
            asset(AssetType.ID_SCANNER, 851.0f, 490.0f, "hall", "ID scanner 2 · Entrance Hall")
            asset(AssetType.ID_SCANNER, 874.0f, 490.0f, "hall", "ID scanner 3 · Entrance Hall")
            asset(AssetType.ID_SCANNER, 896.0f, 490.0f, "hall", "ID scanner 4 · Entrance Hall")
            asset(AssetType.ID_SCANNER, 918.0f, 490.0f, "hall", "ID scanner 5 · Entrance Hall")
            asset(AssetType.ID_SCANNER, 941.0f, 490.0f, "hall", "ID scanner 6 · Entrance Hall")
            asset(AssetType.AIRCON, 1170.0f, 440.0f, "shop", "Air conditioner 1 · Shop (RGO Commercial Rental Stall)", icon = false)
            asset(AssetType.AIRCON, 1218.0f, 440.0f, "shop", "Air conditioner 2 · Shop (RGO Commercial Rental Stall)", icon = false)
        }.build()
    }

    /** The same connected plan is shown for buildings 23, 24 and 25. */
    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId in 23..25 && floor == Floor.GROUND) ground else null
}
