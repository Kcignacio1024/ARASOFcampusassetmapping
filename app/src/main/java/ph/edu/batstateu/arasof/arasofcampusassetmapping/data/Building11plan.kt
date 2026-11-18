package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plans of building 11 (IMT Building / Lobo Building, combined with the Library):
 * ground floor and 2nd floor. Numbers are PIXELS of the plan pictures (1920 x 1080).
 * The ground floor is wider because the Library wraps around the top and the east side;
 * the 2nd floor is the IMT part only (the "Library roof" areas are the roof of the library below).
 * 2nd floor: the CICS Faculty Room includes the connected room next to the CR (only the CR and the IMT
 * Viewdeck are separate rooms). It has one Wi-Fi and two air conditioners on its west wall.
 * Assets are estimates read from the room names: edit freely.
 *
 * FIRE HYDRANTS (ground floor, estimated, confirm with the BFP / campus engineer): one in front of the
 * entrance and one on the east side near the library, on open ground, so every part of the building is
 * within two hose lengths (2 x 20 m) of a hydrant (Fire Code of the Philippines, RA 9514 IRR).
 */
object Building11Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(11, Floor.GROUND, "B11-1", 50.0f, -10.0f, 1350.0f, 1020.0f).apply {
            room("library", "Library", 95.0f, 12.0f, 1168.0f, 762.0f, computers = 10, outline = listOf(95.0f to 12.0f, 1168.0f to 12.0f, 1168.0f to 762.0f, 965.0f to 762.0f, 965.0f to 260.0f, 95.0f to 260.0f))
            room("toilet_m", "Toilet (Male)", 100.0f, 262.0f, 193.0f, 430.0f, computers = 0)
            room("toilet_f", "Toilet (Female)", 200.0f, 262.0f, 291.0f, 430.0f, computers = 0)
            room("vcaa", "Office of the VCAA", 295.0f, 262.0f, 668.0f, 440.0f, computers = 4)
            room("stock", "Stock Room", 500.0f, 522.0f, 592.0f, 590.0f, computers = 0)
            room("coa", "COA Office", 595.0f, 522.0f, 768.0f, 758.0f, computers = 3)
            room("cas", "CAS Faculty Room", 97.0f, 522.0f, 375.0f, 768.0f, computers = 10)
            room("gened", "Gen Ed Office", 775.0f, 262.0f, 960.0f, 760.0f, computers = 8)
            zone("Hallway", ZoneKind.CORRIDOR, 100.0f, 442.0f, 672.0f, 520.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 672.0f, 262.0f, 770.0f, 520.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 375.0f, 520.0f, 500.0f, 765.0f)
            zone("Stairs up to 2nd floor", ZoneKind.STAIRS, 500.0f, 590.0f, 592.0f, 745.0f)
            asset(AssetType.AIRCON, 200.0f, 42.0f, "library", "Air conditioner (north-west)")
            asset(AssetType.AIRCON, 480.0f, 42.0f, "library", "Air conditioner (north)")
            asset(AssetType.AIRCON, 760.0f, 42.0f, "library", "Air conditioner (north-middle)")
            asset(AssetType.AIRCON, 1040.0f, 42.0f, "library", "Air conditioner (north-east)")
            asset(AssetType.AIRCON, 1110.0f, 330.0f, "library", "Air conditioner (east, north)")
            asset(AssetType.AIRCON, 1110.0f, 560.0f, "library", "Air conditioner (east, south)")
            asset(AssetType.WIFI, 250.0f, 200.0f, "library", "")
            asset(AssetType.WIFI, 620.0f, 190.0f, "library", "")
            asset(AssetType.WIFI, 1000.0f, 205.0f, "library", "")
            asset(AssetType.WIFI, 1065.0f, 610.0f, "library", "")
            asset(AssetType.CCTV, 110.0f, 32.0f, "library", "")
            asset(AssetType.CCTV, 640.0f, 32.0f, "library", "")
            asset(AssetType.CCTV, 1150.0f, 32.0f, "library", "")
            asset(AssetType.CCTV, 110.0f, 240.0f, "library", "")
            asset(AssetType.CCTV, 1150.0f, 740.0f, "library", "")
            asset(AssetType.COMPUTER, 850.0f, 190.0f, "library", "Computers (about 10) · Library catalog / study area")
            asset(AssetType.SPECIAL_EQUIP, 718.0f, 232.0f, "library", "Library security gate / book detection (estimated)")
            asset(AssetType.FIRE_ALARM, 300.0f, 95.0f, "library", "")
            asset(AssetType.FIRE_ALARM, 900.0f, 95.0f, "library", "")
            asset(AssetType.FIRE_ALARM, 1060.0f, 385.0f, "library", "")
            asset(AssetType.FIRE_ALARM, 1060.0f, 640.0f, "library", "")
            asset(AssetType.FIRE_LIGHT, 200.0f, 155.0f, "library", "Emergency light · Library (west)")
            asset(AssetType.FIRE_LIGHT, 800.0f, 155.0f, "library", "Emergency light · Library (middle)")
            asset(AssetType.FIRE_LIGHT, 1100.0f, 205.0f, "library", "Emergency light · Library (east, north)")
            asset(AssetType.FIRE_LIGHT, 1100.0f, 700.0f, "library", "Emergency light · Library (east, south)")
            asset(AssetType.EXTINGUISHER, 700.0f, 215.0f, "library", "")
            asset(AssetType.EXTINGUISHER, 1100.0f, 745.0f, "library", "")
            asset(AssetType.EXTINGUISHER, 130.0f, 230.0f, "library", "")
            asset(AssetType.EXIT, 990.0f, 782.0f, "library", "Library exit (south door)")
            asset(AssetType.FIRE_ALARM, 147.0f, 300.0f, "toilet_m", "")
            asset(AssetType.FIRE_ALARM, 245.0f, 300.0f, "toilet_f", "")
            asset(AssetType.AIRCON, 330.0f, 290.0f, "vcaa", "")
            asset(AssetType.AIRCON, 640.0f, 290.0f, "vcaa", "")
            asset(AssetType.WIFI, 480.0f, 300.0f, "vcaa", "")
            asset(AssetType.CCTV, 640.0f, 425.0f, "vcaa", "")
            asset(AssetType.COMPUTER, 480.0f, 390.0f, "vcaa", "Computers (about 4) · Office of the VCAA")
            asset(AssetType.FIRE_ALARM, 400.0f, 400.0f, "vcaa", "")
            asset(AssetType.EXTINGUISHER, 330.0f, 425.0f, "vcaa", "")
            asset(AssetType.FIRE_ALARM, 546.0f, 555.0f, "stock", "")
            asset(AssetType.EXTINGUISHER, 520.0f, 578.0f, "stock", "")
            asset(AssetType.AIRCON, 620.0f, 545.0f, "coa", "")
            asset(AssetType.AIRCON, 745.0f, 545.0f, "coa", "")
            asset(AssetType.WIFI, 683.0f, 590.0f, "coa", "")
            asset(AssetType.CCTV, 745.0f, 735.0f, "coa", "")
            asset(AssetType.COMPUTER, 683.0f, 680.0f, "coa", "Computers (about 3) · COA Office")
            asset(AssetType.FIRE_ALARM, 640.0f, 720.0f, "coa", "")
            asset(AssetType.AIRCON, 125.0f, 545.0f, "cas", "")
            asset(AssetType.AIRCON, 250.0f, 545.0f, "cas", "")
            asset(AssetType.AIRCON, 350.0f, 545.0f, "cas", "")
            asset(AssetType.WIFI, 200.0f, 720.0f, "cas", "")
            asset(AssetType.WIFI, 320.0f, 720.0f, "cas", "")
            asset(AssetType.CCTV, 115.0f, 745.0f, "cas", "")
            asset(AssetType.COMPUTER, 230.0f, 590.0f, "cas", "Computers (about 10) · CAS Faculty Room")
            asset(AssetType.FIRE_ALARM, 150.0f, 670.0f, "cas", "")
            asset(AssetType.FIRE_ALARM, 320.0f, 640.0f, "cas", "")
            asset(AssetType.EXTINGUISHER, 355.0f, 745.0f, "cas", "")
            asset(AssetType.AIRCON, 800.0f, 290.0f, "gened", "")
            asset(AssetType.AIRCON, 930.0f, 290.0f, "gened", "")
            asset(AssetType.AIRCON, 865.0f, 740.0f, "gened", "")
            asset(AssetType.WIFI, 865.0f, 380.0f, "gened", "")
            asset(AssetType.WIFI, 865.0f, 620.0f, "gened", "")
            asset(AssetType.CCTV, 935.0f, 290.0f, "gened", "")
            asset(AssetType.CCTV, 800.0f, 735.0f, "gened", "")
            asset(AssetType.COMPUTER, 865.0f, 560.0f, "gened", "Computers (about 8) · Gen Ed Office")
            asset(AssetType.FIRE_ALARM, 865.0f, 330.0f, "gened", "")
            asset(AssetType.FIRE_ALARM, 865.0f, 700.0f, "gened", "")
            asset(AssetType.EXTINGUISHER, 930.0f, 725.0f, "gened", "")
            asset(AssetType.EXIT, 935.0f, 782.0f, "gened", "Gen Ed Office door (exit)")
            asset(AssetType.CCTV, 150.0f, 482.0f, null, "CCTV camera (hallway, west)")
            asset(AssetType.WIFI, 260.0f, 482.0f, null, "Wi-Fi access point (hallway)")
            asset(AssetType.FIRE_ALARM, 350.0f, 482.0f, null, "Fire alarm (hallway)")
            asset(AssetType.FIRE_LIGHT, 550.0f, 482.0f, null, "Emergency light (hallway)")
            asset(AssetType.EXTINGUISHER, 620.0f, 482.0f, null, "Fire extinguisher (hallway)")
            asset(AssetType.CCTV, 720.0f, 330.0f, null, "CCTV camera (hallway, east)")
            asset(AssetType.FIRE_LIGHT, 720.0f, 420.0f, null, "Emergency light (hallway, east)")
            asset(AssetType.EXTINGUISHER, 715.0f, 500.0f, null, "Fire extinguisher (hallway, east)")
            asset(AssetType.WIFI, 440.0f, 560.0f, null, "Wi-Fi access point (lobby)")
            asset(AssetType.FIRE_LIGHT, 440.0f, 700.0f, null, "Emergency light (lobby)")
            asset(AssetType.CCTV, 400.0f, 745.0f, null, "CCTV camera (entrance)")
            asset(AssetType.FIRE_LIGHT, 546.0f, 660.0f, null, "Emergency light (stairs)")
            asset(AssetType.EXIT, 430.0f, 798.0f, null, "Main entrance (exit)")
            asset(AssetType.HYDRANT, 480.0f, 975.0f, null, "Fire hydrant (outside, in front of the entrance - estimated)")
            asset(AssetType.HYDRANT, 1340.0f, 400.0f, null, "Fire hydrant (outside, east side near the library - estimated)")
        }.build(note = "The Library is combined with this building: it wraps around the top and the east side.")
    }

    private val second: FloorData by lazy {
        PlanBuilder(11, Floor.SECOND, "B11-2", 110.0f, 20.0f, 860.0f, 1075.0f).apply {
            room("viewdeck", "IMT Viewdeck", 405.0f, 60.0f, 738.0f, 300.0f, computers = 0)
            room("cr", "Comfort Room / CR", 160.0f, 310.0f, 328.0f, 510.0f, computers = 0)
            room("faculty", "CICS Faculty Room", 160.0f, 310.0f, 480.0f, 920.0f, computers = 12, outline = listOf(332.0f to 310.0f, 480.0f to 310.0f, 480.0f to 920.0f, 160.0f to 882.0f, 160.0f to 512.0f, 332.0f to 512.0f))
            room("dean", "CICS Deans Office", 745.0f, 310.0f, 955.0f, 906.0f, computers = 3, outline = listOf(748.0f to 310.0f, 955.0f to 310.0f, 955.0f to 882.0f, 745.0f to 906.0f, 745.0f to 310.0f))
            room("balcony", "IMT Balcony", 150.0f, 882.0f, 960.0f, 1065.0f, computers = 0, outline = listOf(150.0f to 882.0f, 470.0f to 925.0f, 738.0f to 930.0f, 750.0f to 906.0f, 960.0f to 882.0f, 900.0f to 950.0f, 800.0f to 1005.0f, 680.0f to 1045.0f, 565.0f to 1065.0f, 450.0f to 1045.0f, 320.0f to 1005.0f, 220.0f to 950.0f))
            zone("Library roof", ZoneKind.ROOFTOP, 160.0f, 55.0f, 398.0f, 300.0f)
            zone("Library roof", ZoneKind.ROOFTOP, 748.0f, 55.0f, 955.0f, 300.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 485.0f, 305.0f, 738.0f, 612.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 485.0f, 612.0f, 628.0f, 930.0f)
            zone("Stairs down to ground floor", ZoneKind.STAIRS, 628.0f, 612.0f, 738.0f, 832.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 628.0f, 832.0f, 738.0f, 930.0f)
            asset(AssetType.CCTV, 430.0f, 90.0f, "viewdeck", "")
            asset(AssetType.CCTV, 715.0f, 90.0f, "viewdeck", "")
            asset(AssetType.WIFI, 570.0f, 230.0f, "viewdeck", "")
            asset(AssetType.FIRE_LIGHT, 470.0f, 260.0f, "viewdeck", "Emergency light · IMT Viewdeck")
            asset(AssetType.EXTINGUISHER, 700.0f, 270.0f, "viewdeck", "")
            asset(AssetType.FIRE_ALARM, 245.0f, 380.0f, "cr", "")
            asset(AssetType.CCTV, 190.0f, 860.0f, "faculty", "")
            asset(AssetType.CCTV, 440.0f, 530.0f, "faculty", "")
            asset(AssetType.COMPUTER, 290.0f, 610.0f, "faculty", "Computers (about 12) · CICS Faculty Room")
            asset(AssetType.FIRE_ALARM, 200.0f, 700.0f, "faculty", "")
            asset(AssetType.FIRE_ALARM, 400.0f, 800.0f, "faculty", "")
            asset(AssetType.EXTINGUISHER, 455.0f, 640.0f, "faculty", "")
            asset(AssetType.AIRCON, 800.0f, 360.0f, "dean", "")
            asset(AssetType.AIRCON, 900.0f, 360.0f, "dean", "")
            asset(AssetType.WIFI, 850.0f, 700.0f, "dean", "")
            asset(AssetType.CCTV, 930.0f, 340.0f, "dean", "")
            asset(AssetType.COMPUTER, 850.0f, 470.0f, "dean", "Computers (about 3) · CICS Deans Office")
            asset(AssetType.FIRE_ALARM, 850.0f, 780.0f, "dean", "")
            asset(AssetType.EXTINGUISHER, 780.0f, 820.0f, "dean", "")
            asset(AssetType.CCTV, 560.0f, 340.0f, null, "CCTV camera (hallway, north)")
            asset(AssetType.WIFI, 610.0f, 500.0f, null, "Wi-Fi access point (hallway)")
            asset(AssetType.FIRE_LIGHT, 560.0f, 420.0f, null, "Emergency light (hallway)")
            asset(AssetType.FIRE_ALARM, 690.0f, 400.0f, null, "Fire alarm (hallway)")
            asset(AssetType.EXTINGUISHER, 700.0f, 590.0f, null, "Fire extinguisher (near stairs)")
            asset(AssetType.CCTV, 530.0f, 760.0f, null, "CCTV camera (hallway, south)")
            asset(AssetType.FIRE_LIGHT, 555.0f, 860.0f, null, "Emergency light (hallway, south)")
            asset(AssetType.EXTINGUISHER, 520.0f, 650.0f, null, "Fire extinguisher (hallway)")
            asset(AssetType.FIRE_LIGHT, 683.0f, 740.0f, null, "Emergency light (stairs)")
            asset(AssetType.EXIT, 683.0f, 800.0f, null, "Exit (stairs down to ground floor)")
            asset(AssetType.CCTV, 260.0f, 960.0f, "balcony", "")
            asset(AssetType.CCTV, 860.0f, 950.0f, "balcony", "")
            asset(AssetType.WIFI, 565.0f, 1000.0f, "balcony", "")
            asset(AssetType.FIRE_LIGHT, 420.0f, 990.0f, "balcony", "Emergency light · IMT Balcony (west)")
            asset(AssetType.FIRE_LIGHT, 720.0f, 990.0f, "balcony", "Emergency light · IMT Balcony (east)")
            asset(AssetType.AIRCON, 172.0f, 610.0f, "faculty", "Air conditioner (west wall, north)")
            asset(AssetType.AIRCON, 172.0f, 790.0f, "faculty", "Air conditioner (west wall, south)")
            asset(AssetType.WIFI, 320.0f, 690.0f, "faculty", "Wi-Fi access point · CICS Faculty Room (only one)")
            asset(AssetType.FIRE_ALARM, 400.0f, 420.0f, "faculty", "")
        }.build(note = "This floor is the IMT part only. The Library roof areas are the roof of the Library below.")
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 11) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            Floor.THIRD -> null
            Floor.FOURTH -> null
        }
    }
}