package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 2 (VIP Corals - Nasugbu Marine Research Station).
 * This building only has a ground floor.
 * Numbers are PIXELS of the plan picture (1920 x 1080). Assets are rough estimates: edit freely.
 *
 * The ramp is drawn like the drawing: four runs joined by turning landings (zig-zag).
 */
object Building2Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(2, Floor.GROUND, "B2-1", 505.0f, 5.0f, 675.0f, 1050.0f).apply {
            room("wetlab", "Wet Laboratory", 552.0f, 152.0f, 780.0f, 440.0f, computers = 0)
            room("instrument", "Instrumentation Room", 552.0f, 440.0f, 780.0f, 725.0f, computers = 3)
            room("accommodation", "Accommodation Room", 900.0f, 272.0f, 1125.0f, 403.0f, computers = 0)
            room("conference", "Conference Room", 900.0f, 405.0f, 1125.0f, 553.0f, computers = 0)
            room("lobby", "Lobby", 782.0f, 557.0f, 1068.0f, 668.0f, computers = 0)
            room("entrance", "Main Entrance", 782.0f, 672.0f, 1068.0f, 785.0f, computers = 0)
            room("toilet_a", "Male / Female Toilet (west)", 842.0f, 163.0f, 1000.0f, 263.0f, computers = 0)
            room("toilet_b", "Male / Female Toilet (east)", 1003.0f, 110.0f, 1137.0f, 265.0f, computers = 0)
            zone("Hallway", ZoneKind.CORRIDOR, 782.0f, 272.0f, 897.0f, 556.0f)
            zone("Restroom entrance", ZoneKind.CORRIDOR, 785.0f, 110.0f, 1002.0f, 263.0f, outline = listOf(785.0f to 110.0f, 1002.0f to 110.0f, 1002.0f to 163.0f, 842.0f to 163.0f, 842.0f to 263.0f, 785.0f to 263.0f))
            zone("Stairs up", ZoneKind.STAIRS, 842.0f, 33.0f, 935.0f, 108.0f)
            zone("Entrance stairs", ZoneKind.STAIRS, 812.0f, 795.0f, 906.0f, 882.0f)
            zone("Landing", ZoneKind.STAIRS, 812.0f, 882.0f, 912.0f, 1053.0f)
            zone("Ramp landing", ZoneKind.RAMP, 917.0f, 797.0f, 970.0f, 850.0f)
            zone("Ramp", ZoneKind.RAMP, 970.0f, 796.0f, 1078.0f, 850.0f)
            zone("Ramp turn", ZoneKind.RAMP, 1078.0f, 798.0f, 1135.0f, 907.0f)
            zone("Ramp", ZoneKind.RAMP, 970.0f, 857.0f, 1078.0f, 907.0f)
            zone("Ramp turn", ZoneKind.RAMP, 917.0f, 857.0f, 970.0f, 993.0f)
            zone("Ramp", ZoneKind.RAMP, 970.0f, 943.0f, 1078.0f, 992.0f)
            zone("Ramp turn", ZoneKind.RAMP, 1078.0f, 943.0f, 1135.0f, 1050.0f)
            zone("Ramp up", ZoneKind.RAMP, 913.0f, 1000.0f, 1078.0f, 1050.0f)
            asset(AssetType.AIRCON, 572.0f, 225.0f, "wetlab", "Air conditioner (west wall, north)")
            asset(AssetType.AIRCON, 572.0f, 370.0f, "wetlab", "Air conditioner (west wall, south)")
            asset(AssetType.WIFI, 665.0f, 195.0f, "wetlab", "")
            asset(AssetType.SPECIAL_EQUIP, 665.0f, 390.0f, "wetlab", "Laboratory benches / aquarium tanks")
            asset(AssetType.FIRE_ALARM, 665.0f, 265.0f, "wetlab", "")
            asset(AssetType.CCTV, 762.0f, 175.0f, "wetlab", "")
            asset(AssetType.EXTINGUISHER, 762.0f, 415.0f, "wetlab", "")
            asset(AssetType.AIRCON, 572.0f, 515.0f, "instrument", "Air conditioner (west wall, north)")
            asset(AssetType.AIRCON, 572.0f, 655.0f, "instrument", "Air conditioner (west wall, south)")
            asset(AssetType.WIFI, 665.0f, 490.0f, "instrument", "")
            asset(AssetType.SPECIAL_EQUIP, 620.0f, 650.0f, "instrument", "Analytical instruments")
            asset(AssetType.COMPUTER, 720.0f, 640.0f, "instrument", "Computers (about 3) · Instrumentation Room")
            asset(AssetType.FIRE_ALARM, 665.0f, 560.0f, "instrument", "")
            asset(AssetType.CCTV, 762.0f, 705.0f, "instrument", "")
            asset(AssetType.AIRCON, 1105.0f, 320.0f, "accommodation", "")
            asset(AssetType.WIFI, 960.0f, 375.0f, "accommodation", "")
            asset(AssetType.FIRE_ALARM, 1010.0f, 300.0f, "accommodation", "")
            asset(AssetType.AIRCON, 1105.0f, 445.0f, "conference", "Air conditioner (north)")
            asset(AssetType.AIRCON, 1105.0f, 510.0f, "conference", "Air conditioner (south)")
            asset(AssetType.WIFI, 960.0f, 445.0f, "conference", "")
            asset(AssetType.PROJECTOR, 1010.0f, 525.0f, "conference", "")
            asset(AssetType.FIRE_ALARM, 1010.0f, 430.0f, "conference", "")
            asset(AssetType.CCTV, 1105.0f, 540.0f, "conference", "")
            asset(AssetType.CCTV, 985.0f, 573.0f, "lobby", "")
            asset(AssetType.FIRE_ALARM, 920.0f, 585.0f, "lobby", "")
            asset(AssetType.WIFI, 930.0f, 640.0f, "lobby", "")
            asset(AssetType.EXTINGUISHER, 1045.0f, 605.0f, "lobby", "")
            asset(AssetType.FIRE_LIGHT, 850.0f, 640.0f, "lobby", "Emergency light · Lobby")
            asset(AssetType.CCTV, 1050.0f, 688.0f, "entrance", "")
            asset(AssetType.FIRE_LIGHT, 850.0f, 705.0f, "entrance", "Emergency light · Main Entrance")
            asset(AssetType.EXTINGUISHER, 1040.0f, 762.0f, "entrance", "")
            asset(AssetType.EXIT, 930.0f, 775.0f, "entrance", "Main entrance (exit)")
            asset(AssetType.FIRE_ALARM, 960.0f, 235.0f, "toilet_a", "")
            asset(AssetType.FIRE_ALARM, 1070.0f, 235.0f, "toilet_b", "")
            asset(AssetType.FIRE_LIGHT, 920.0f, 135.0f, null, "Emergency light (restroom entrance)")
            asset(AssetType.EXTINGUISHER, 815.0f, 200.0f, null, "Fire extinguisher (restroom entrance)")
            asset(AssetType.EXIT, 815.0f, 290.0f, null, "Exit (north end of hallway)")
            asset(AssetType.CCTV, 840.0f, 335.0f, null, "CCTV camera (hallway)")
            asset(AssetType.FIRE_LIGHT, 840.0f, 500.0f, null, "Emergency light (hallway)")
            asset(AssetType.EXTINGUISHER, 800.0f, 520.0f, null, "Fire extinguisher (hallway)")
            asset(AssetType.EXIT, 890.0f, 60.0f, null, "Exit (north stairs)")
            asset(AssetType.FIRE_LIGHT, 860.0f, 840.0f, null, "Emergency light (entrance stairs)")
            asset(AssetType.CCTV, 1105.0f, 830.0f, null, "CCTV camera (ramp)")
            asset(AssetType.FIRE_LIGHT, 1020.0f, 882.0f, null, "Emergency light (ramp, middle)")
            asset(AssetType.FIRE_LIGHT, 1020.0f, 968.0f, null, "Emergency light (ramp, lower)")
            asset(AssetType.EXIT, 1025.0f, 1030.0f, null, "Exit (ramp)")
            asset(AssetType.HYDRANT, 690.0f, 900.0f, null, "Fire hydrant (outside the building, estimated)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId == 2 && floor == Floor.GROUND) ground else null
}