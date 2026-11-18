package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plans of building 4 (Rosario Cafeteria): ground floor and 2nd floor.
 * Numbers are PIXELS of the plan pictures (1920 x 1080).
 *
 * Seating follows the legend on the plan:
 *   - TABLE_SET = "table with seats": 14 on the ground floor, 9 on the 2nd floor
 *   - SEAT      = a single seat: 24 on the ground floor
 *   - TABLE     = a table only (standing dining): 8 on the ground floor
 * There are NO air conditioners in this building, so cooling is by fans (estimated).
 * Everything else (kitchen equipment, fans, Wi-Fi, CCTV, fire safety) is an estimate: edit freely.
 *
 * FIRE HYDRANTS (estimated, confirm with the BFP / campus engineer): two on open ground, east and
 * south-east of the building, so every part is within two hose lengths (2 x 20 m) of a hydrant
 * (Fire Code of the Philippines, RA 9514 IRR).
 */
object Building4Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(4, Floor.GROUND, "B4-1", 100.0f, 110.0f, 1080.0f, 960.0f).apply {
            room("hall", "Dining Hall", 210.0f, 282.0f, 927.0f, 655.0f, computers = 0, outline = listOf(210.0f to 282.0f, 927.0f to 282.0f, 927.0f to 655.0f, 372.0f to 655.0f, 372.0f to 405.0f, 210.0f to 405.0f))
            room("cashier", "Cashier 1 / Serving Line", 210.0f, 405.0f, 372.0f, 655.0f, computers = 1)
            room("kitchen", "Kitchen", 297.0f, 145.0f, 568.0f, 272.0f, computers = 0)
            room("stock", "Stockroom", 150.0f, 143.0f, 253.0f, 213.0f, computers = 0)
            room("cr", "CR", 150.0f, 213.0f, 208.0f, 330.0f, computers = 0)
            room("amp", "AMP (Amphitheater) Dining Area", 365.0f, 662.0f, 762.0f, 857.0f, computers = 0)
            room("stall1", "Stall 1", 376.0f, 857.0f, 504.0f, 975.0f, computers = 0)
            room("stall2", "Stall 2", 632.0f, 857.0f, 758.0f, 975.0f, computers = 0)
            zone("Exit passage", ZoneKind.CORRIDOR, 255.0f, 145.0f, 295.0f, 272.0f)
            zone("West exit passage", ZoneKind.CORRIDOR, 150.0f, 340.0f, 207.0f, 655.0f)
            zone("Stairs to 2nd floor", ZoneKind.STAIRS, 487.0f, 283.0f, 665.0f, 340.0f)
            zone("East entrance", ZoneKind.CORRIDOR, 927.0f, 460.0f, 985.0f, 660.0f)
            zone("Entrance", ZoneKind.CORRIDOR, 504.0f, 857.0f, 632.0f, 980.0f)
            asset(AssetType.TABLE_SET, 500.0f, 413.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 500.0f, 477.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 500.0f, 541.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 595.0f, 413.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 595.0f, 477.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 595.0f, 541.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 686.0f, 413.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 686.0f, 477.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 686.0f, 541.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 777.0f, 413.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 777.0f, 477.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 777.0f, 541.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 868.0f, 413.0f, "hall", "Table with seats")
            asset(AssetType.TABLE_SET, 868.0f, 477.0f, "hall", "Table with seats")
            asset(AssetType.SEAT, 407.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 436.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 465.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 492.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 650.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 677.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 706.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 733.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 790.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 818.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 846.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 874.0f, 632.0f, "hall", "Seat")
            asset(AssetType.SEAT, 410.0f, 716.0f, "amp", "Seat")
            asset(AssetType.SEAT, 725.0f, 716.0f, "amp", "Seat")
            asset(AssetType.SEAT, 410.0f, 743.0f, "amp", "Seat")
            asset(AssetType.SEAT, 725.0f, 743.0f, "amp", "Seat")
            asset(AssetType.SEAT, 410.0f, 770.0f, "amp", "Seat")
            asset(AssetType.SEAT, 725.0f, 770.0f, "amp", "Seat")
            asset(AssetType.SEAT, 410.0f, 797.0f, "amp", "Seat")
            asset(AssetType.SEAT, 725.0f, 797.0f, "amp", "Seat")
            asset(AssetType.SEAT, 447.0f, 698.0f, "amp", "Seat")
            asset(AssetType.SEAT, 480.0f, 698.0f, "amp", "Seat")
            asset(AssetType.SEAT, 657.0f, 698.0f, "amp", "Seat")
            asset(AssetType.SEAT, 690.0f, 698.0f, "amp", "Seat")
            asset(AssetType.TABLE, 464.0f, 750.0f, "amp", "Table (standing dining)")
            asset(AssetType.TABLE, 464.0f, 802.0f, "amp", "Table (standing dining)")
            asset(AssetType.TABLE, 524.0f, 750.0f, "amp", "Table (standing dining)")
            asset(AssetType.TABLE, 524.0f, 802.0f, "amp", "Table (standing dining)")
            asset(AssetType.TABLE, 613.0f, 750.0f, "amp", "Table (standing dining)")
            asset(AssetType.TABLE, 613.0f, 802.0f, "amp", "Table (standing dining)")
            asset(AssetType.TABLE, 672.0f, 750.0f, "amp", "Table (standing dining)")
            asset(AssetType.TABLE, 672.0f, 802.0f, "amp", "Table (standing dining)")
            asset(AssetType.SPECIAL_EQUIP, 470.0f, 172.0f, "kitchen", "Cooking stoves / burners (4)")
            asset(AssetType.SPECIAL_EQUIP, 344.0f, 173.0f, "kitchen", "Sinks / dishwashing station")
            asset(AssetType.SPECIAL_EQUIP, 470.0f, 205.0f, "kitchen", "Exhaust hood & fire suppression system (estimated)")
            asset(AssetType.SPECIAL_EQUIP, 560.0f, 235.0f, "kitchen", "Refrigerator & freezer (estimated)")
            asset(AssetType.SPECIAL_EQUIP, 310.0f, 245.0f, "kitchen", "LPG tanks & gas leak detector (estimated)")
            asset(AssetType.FIRE_ALARM, 430.0f, 232.0f, "kitchen", "Heat / smoke detector · Kitchen")
            asset(AssetType.EXTINGUISHER, 540.0f, 258.0f, "kitchen", "Fire extinguisher (kitchen, K-class recommended)")
            asset(AssetType.EXIT, 277.0f, 185.0f, null, "Kitchen exit (west)")
            asset(AssetType.SPECIAL_EQUIP, 200.0f, 178.0f, "stock", "Storage shelves & freezer (estimated)")
            asset(AssetType.FIRE_ALARM, 235.0f, 160.0f, "stock", "")
            asset(AssetType.COMPUTER, 260.0f, 520.0f, "cashier", "Cashier POS / computer (1)")
            asset(AssetType.SPECIAL_EQUIP, 350.0f, 500.0f, "cashier", "Serving line & food warmers (estimated)")
            asset(AssetType.CCTV, 240.0f, 640.0f, "cashier", "")
            asset(AssetType.FAN, 390.0f, 345.0f, "hall", "")
            asset(AssetType.FAN, 590.0f, 345.0f, "hall", "")
            asset(AssetType.FAN, 790.0f, 345.0f, "hall", "")
            asset(AssetType.FAN, 390.0f, 610.0f, "hall", "")
            asset(AssetType.FAN, 590.0f, 600.0f, "hall", "")
            asset(AssetType.FAN, 790.0f, 600.0f, "hall", "")
            asset(AssetType.WIFI, 330.0f, 300.0f, "hall", "")
            asset(AssetType.WIFI, 850.0f, 640.0f, "hall", "")
            asset(AssetType.CCTV, 225.0f, 300.0f, "hall", "")
            asset(AssetType.CCTV, 915.0f, 300.0f, "hall", "")
            asset(AssetType.CCTV, 915.0f, 640.0f, "hall", "")
            asset(AssetType.FIRE_ALARM, 700.0f, 300.0f, "hall", "")
            asset(AssetType.FIRE_ALARM, 880.0f, 600.0f, "hall", "")
            asset(AssetType.FIRE_LIGHT, 300.0f, 620.0f, "hall", "Emergency light · Dining Hall (west)")
            asset(AssetType.FIRE_LIGHT, 850.0f, 620.0f, "hall", "Emergency light · Dining Hall (east)")
            asset(AssetType.EXTINGUISHER, 400.0f, 310.0f, "hall", "Fire extinguisher (near kitchen)")
            asset(AssetType.EXTINGUISHER, 900.0f, 590.0f, "hall", "Fire extinguisher (near east exit)")
            asset(AssetType.SPECIAL_EQUIP, 890.0f, 520.0f, "hall", "Handwashing station (estimated)")
            asset(AssetType.SPECIAL_EQUIP, 262.0f, 350.0f, "hall", "Water dispenser (estimated)")
            asset(AssetType.EXIT, 925.0f, 560.0f, "hall", "East entrance / exit")
            asset(AssetType.EXIT, 183.0f, 420.0f, null, "West exit")
            asset(AssetType.FAN, 430.0f, 832.0f, "amp", "")
            asset(AssetType.FAN, 520.0f, 832.0f, "amp", "")
            asset(AssetType.FAN, 620.0f, 832.0f, "amp", "")
            asset(AssetType.FAN, 700.0f, 832.0f, "amp", "")
            asset(AssetType.WIFI, 568.0f, 690.0f, "amp", "")
            asset(AssetType.CCTV, 385.0f, 672.0f, "amp", "")
            asset(AssetType.CCTV, 745.0f, 672.0f, "amp", "")
            asset(AssetType.FIRE_ALARM, 568.0f, 745.0f, "amp", "")
            asset(AssetType.FIRE_LIGHT, 568.0f, 720.0f, "amp", "Emergency light · AMP Dining Area")
            asset(AssetType.SPECIAL_EQUIP, 430.0f, 925.0f, "stall1", "Food stall counter, display warmer & refrigerator (estimated)")
            asset(AssetType.FIRE_ALARM, 450.0f, 885.0f, "stall1", "")
            asset(AssetType.EXTINGUISHER, 492.0f, 885.0f, "stall1", "")
            asset(AssetType.SPECIAL_EQUIP, 690.0f, 925.0f, "stall2", "Food stall counter, display warmer & refrigerator (estimated)")
            asset(AssetType.FIRE_ALARM, 680.0f, 885.0f, "stall2", "")
            asset(AssetType.EXTINGUISHER, 645.0f, 885.0f, "stall2", "")
            asset(AssetType.EXIT, 568.0f, 968.0f, null, "South entrance / exit")
            asset(AssetType.HYDRANT, 1100.0f, 520.0f, null, "Fire hydrant (outside, east of the entrance - estimated)")
            asset(AssetType.HYDRANT, 900.0f, 880.0f, null, "Fire hydrant (outside, south-east - estimated)")
        }.build()
    }

    private val second: FloorData by lazy {
        PlanBuilder(4, Floor.SECOND, "B4-2", 820.0f, 110.0f, 960.0f, 820.0f).apply {
            room("hall2", "Dining Hall (2nd floor)", 1130.0f, 190.0f, 1705.0f, 875.0f, computers = 0, outline = listOf(1225.0f to 190.0f, 1705.0f to 190.0f, 1705.0f to 875.0f, 1130.0f to 875.0f, 1130.0f to 305.0f, 1225.0f to 305.0f))
            zone("Stairs to 1st floor", ZoneKind.STAIRS, 888.0f, 198.0f, 1222.0f, 302.0f)
            asset(AssetType.TABLE_SET, 1240.0f, 405.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1423.0f, 405.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1605.0f, 405.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1240.0f, 580.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1423.0f, 580.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1605.0f, 580.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1240.0f, 755.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1423.0f, 755.0f, "hall2", "Table with seats")
            asset(AssetType.TABLE_SET, 1605.0f, 755.0f, "hall2", "Table with seats")
            asset(AssetType.FAN, 1330.0f, 300.0f, "hall2", "")
            asset(AssetType.FAN, 1515.0f, 300.0f, "hall2", "")
            asset(AssetType.FAN, 1330.0f, 490.0f, "hall2", "")
            asset(AssetType.FAN, 1515.0f, 490.0f, "hall2", "")
            asset(AssetType.FAN, 1330.0f, 667.0f, "hall2", "")
            asset(AssetType.FAN, 1515.0f, 667.0f, "hall2", "")
            asset(AssetType.WIFI, 1650.0f, 300.0f, "hall2", "")
            asset(AssetType.CCTV, 1150.0f, 320.0f, "hall2", "")
            asset(AssetType.CCTV, 1690.0f, 860.0f, "hall2", "")
            asset(AssetType.FIRE_ALARM, 1400.0f, 860.0f, "hall2", "")
            asset(AssetType.FIRE_ALARM, 1660.0f, 230.0f, "hall2", "")
            asset(AssetType.EXTINGUISHER, 1260.0f, 260.0f, "hall2", "Fire extinguisher (near the stairs)")
            asset(AssetType.EXTINGUISHER, 1675.0f, 830.0f, "hall2", "Fire extinguisher (far corner)")
            asset(AssetType.FIRE_LIGHT, 1150.0f, 860.0f, "hall2", "Emergency light · Dining Hall (west)")
            asset(AssetType.FIRE_LIGHT, 1690.0f, 220.0f, "hall2", "Emergency light · Dining Hall (north-east)")
            asset(AssetType.EXIT, 1055.0f, 250.0f, null, "Exit (stairs down to the 1st floor)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 4) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            Floor.THIRD -> null
            Floor.FOURTH -> null
        }
    }
}