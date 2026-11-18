package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 8 (Infirmary). This building only has a ground floor.
 * Numbers are PIXELS of the plan picture (1920 x 1080). The frame is taller than the picture so the
 * outside fire hydrants can be shown on the plan. Assets are estimates: edit freely.
 *
 * FIRE HYDRANTS (estimated, please confirm with the BFP / campus engineer):
 *  - Fire Code of the Philippines (RA 9514) IRR: hydrants should be placed so that safe access to and
 *    deployment from them is always possible, and so that no more than two hose lengths (one hose
 *    length = 20 m) need to be laid from a hydrant; the maximum spacing between hydrants is 250 m.
 *  - RA 4136 (Land Transportation and Traffic Code): keep 4 m clear around a hydrant, so these are
 *    placed outside the building on open ground, not under the porch.
 *  The building is about 30 m long, so one hydrant in front of the entrance and one at the west end
 *    (near the storage room) keep every room well within two hose lengths.
 */
object Building8Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(8, Floor.GROUND, "B8-1", 30.0f, 30.0f, 1885.0f, 1135.0f).apply {
            room("storage", "Storage Room", 52.0f, 55.0f, 305.0f, 685.0f, computers = 0)
            room("sto", "Storage (STO.)", 310.0f, 62.0f, 559.0f, 306.0f, computers = 0, outline = listOf(310.0f to 62.0f, 559.0f to 62.0f, 559.0f to 306.0f, 445.0f to 306.0f, 445.0f to 158.0f, 310.0f to 158.0f))
            room("toilets_w", "Toilets (west)", 312.0f, 158.0f, 445.0f, 306.0f, computers = 0)
            room("unnamed_w", "Room (name not shown on plan)", 562.0f, 62.0f, 810.0f, 306.0f, computers = 0)
            room("procurement", "Procurement and Supply Office", 308.0f, 378.0f, 805.0f, 685.0f, computers = 6)
            room("pantry", "Pantry", 812.0f, 385.0f, 968.0f, 555.0f, computers = 0)
            room("toilets_m", "Toilets (middle)", 812.0f, 558.0f, 968.0f, 685.0f, computers = 0)
            room("male", "Male Ward", 972.0f, 383.0f, 1170.0f, 683.0f, computers = 0)
            room("female", "Female Ward", 1178.0f, 383.0f, 1385.0f, 683.0f, computers = 0)
            room("receiving", "Receiving Area", 1388.0f, 380.0f, 1580.0f, 685.0f, computers = 2)
            room("emergency", "Emergency Room", 1583.0f, 380.0f, 1812.0f, 685.0f, computers = 0)
            room("record", "Record Section & Central Supply Room", 893.0f, 65.0f, 1137.0f, 290.0f, computers = 2)
            room("dental", "Dental Room", 1143.0f, 68.0f, 1307.0f, 290.0f, computers = 0)
            room("unnamed_s", "Room (name not shown on plan)", 1310.0f, 68.0f, 1390.0f, 290.0f, computers = 1)
            room("nurse", "Nurse Office", 1393.0f, 68.0f, 1583.0f, 290.0f, computers = 2, outline = listOf(1393.0f to 68.0f, 1583.0f to 68.0f, 1583.0f to 215.0f, 1507.0f to 215.0f, 1507.0f to 290.0f, 1393.0f to 290.0f))
            room("unnamed_e", "Room (name not shown on plan)", 1587.0f, 68.0f, 1803.0f, 215.0f, computers = 0)
            room("consult", "Consultation Room", 1587.0f, 217.0f, 1807.0f, 370.0f, computers = 1)
            room("toilet_e", "Toilet", 1813.0f, 177.0f, 1900.0f, 240.0f, computers = 0)
            room("entrance", "Entrance", 1388.0f, 690.0f, 1812.0f, 820.0f, computers = 0)
            zone("Hallway", ZoneKind.CORRIDOR, 308.0f, 62.0f, 1583.0f, 378.0f, outline = listOf(308.0f to 306.0f, 812.0f to 306.0f, 812.0f to 62.0f, 893.0f to 62.0f, 893.0f to 292.0f, 1507.0f to 292.0f, 1507.0f to 215.0f, 1583.0f to 215.0f, 1583.0f to 378.0f, 308.0f to 378.0f))
            zone("Ramp up", ZoneKind.RAMP, 1383.0f, 820.0f, 1450.0f, 885.0f)
            zone("Ramp up", ZoneKind.RAMP, 1690.0f, 820.0f, 1815.0f, 885.0f)
            asset(AssetType.AIRCON, 1277.0f, 58.0f, "dental", "")
            asset(AssetType.SPECIAL_EQUIP, 1225.0f, 190.0f, "dental", "Dental chair & dental unit")
            asset(AssetType.SPECIAL_EQUIP, 1250.0f, 105.0f, "dental", "Sterilizer / autoclave (estimated)")
            asset(AssetType.FIRE_ALARM, 1180.0f, 120.0f, "dental", "")
            asset(AssetType.WIFI, 1270.0f, 250.0f, "dental", "")
            asset(AssetType.AIRCON, 1812.0f, 610.0f, "emergency", "")
            asset(AssetType.SPECIAL_EQUIP, 1640.0f, 600.0f, "emergency", "Emergency equipment (oxygen, AED, stretcher)")
            asset(AssetType.FIRE_ALARM, 1700.0f, 440.0f, "emergency", "")
            asset(AssetType.CCTV, 1600.0f, 405.0f, "emergency", "")
            asset(AssetType.WIFI, 1720.0f, 640.0f, "emergency", "")
            asset(AssetType.EXTINGUISHER, 1605.0f, 660.0f, "emergency", "")
            asset(AssetType.FIRE_LIGHT, 1790.0f, 660.0f, "emergency", "Emergency light · Emergency Room")
            asset(AssetType.EXIT, 1745.0f, 690.0f, "emergency", "Emergency room door to entrance (exit)")
            asset(AssetType.AIRCON, 1800.0f, 285.0f, "consult", "")
            asset(AssetType.WIFI, 1650.0f, 335.0f, "consult", "")
            asset(AssetType.FIRE_ALARM, 1720.0f, 262.0f, "consult", "")
            asset(AssetType.SPECIAL_EQUIP, 1700.0f, 335.0f, "consult", "Examination couch / medical equipment")
            asset(AssetType.COMPUTER, 1640.0f, 252.0f, "consult", "Computer (1) · Consultation Room")
            asset(AssetType.AIRCON, 1490.0f, 58.0f, "nurse", "")
            asset(AssetType.COMPUTER, 1550.0f, 110.0f, "nurse", "Computers (about 2) · Nurse Office")
            asset(AssetType.FIRE_ALARM, 1460.0f, 200.0f, "nurse", "")
            asset(AssetType.WIFI, 1440.0f, 150.0f, "nurse", "")
            asset(AssetType.SPECIAL_EQUIP, 1540.0f, 175.0f, "nurse", "Medicine cabinet / vaccine refrigerator (estimated)")
            asset(AssetType.FIRE_ALARM, 1700.0f, 140.0f, "unnamed_e", "")
            asset(AssetType.FIRE_LIGHT, 1780.0f, 190.0f, "unnamed_e", "Emergency light · Room (name not shown)")
            asset(AssetType.AIRCON, 1097.0f, 58.0f, "record", "")
            asset(AssetType.COMPUTER, 1040.0f, 180.0f, "record", "Computers (about 2) · Record Section")
            asset(AssetType.CCTV, 1120.0f, 100.0f, "record", "")
            asset(AssetType.FIRE_ALARM, 1015.0f, 110.0f, "record", "")
            asset(AssetType.EXTINGUISHER, 1060.0f, 275.0f, "record", "")
            asset(AssetType.COMPUTER, 1355.0f, 118.0f, "unnamed_s", "Computer (1) · Room (name not shown)")
            asset(AssetType.FIRE_ALARM, 1350.0f, 230.0f, "unnamed_s", "")
            asset(AssetType.AIRCON, 1015.0f, 683.0f, "male", "")
            asset(AssetType.WIFI, 1070.0f, 430.0f, "male", "")
            asset(AssetType.FIRE_ALARM, 1070.0f, 500.0f, "male", "")
            asset(AssetType.SPECIAL_EQUIP, 1070.0f, 565.0f, "male", "Patient beds (estimated 4)")
            asset(AssetType.FIRE_LIGHT, 1070.0f, 630.0f, "male", "Emergency light · Male Ward")
            asset(AssetType.EXTINGUISHER, 1030.0f, 410.0f, "male", "")
            asset(AssetType.AIRCON, 1347.0f, 683.0f, "female", "")
            asset(AssetType.WIFI, 1280.0f, 430.0f, "female", "")
            asset(AssetType.FIRE_ALARM, 1280.0f, 500.0f, "female", "")
            asset(AssetType.SPECIAL_EQUIP, 1280.0f, 565.0f, "female", "Patient beds (estimated 4)")
            asset(AssetType.FIRE_LIGHT, 1280.0f, 630.0f, "female", "Emergency light · Female Ward")
            asset(AssetType.EXTINGUISHER, 1340.0f, 410.0f, "female", "")
            asset(AssetType.AIRCON, 1405.0f, 683.0f, "receiving", "")
            asset(AssetType.CCTV, 1500.0f, 405.0f, "receiving", "")
            asset(AssetType.WIFI, 1480.0f, 560.0f, "receiving", "")
            asset(AssetType.COMPUTER, 1450.0f, 610.0f, "receiving", "Computers (about 2) · Receiving Area")
            asset(AssetType.FIRE_ALARM, 1500.0f, 445.0f, "receiving", "")
            asset(AssetType.EXTINGUISHER, 1545.0f, 650.0f, "receiving", "")
            asset(AssetType.COMPUTER, 560.0f, 480.0f, "procurement", "Computers (about 6) · Procurement and Supply Office")
            asset(AssetType.WIFI, 560.0f, 420.0f, "procurement", "")
            asset(AssetType.CCTV, 330.0f, 645.0f, "procurement", "")
            asset(AssetType.CCTV, 790.0f, 410.0f, "procurement", "")
            asset(AssetType.FIRE_ALARM, 560.0f, 540.0f, "procurement", "")
            asset(AssetType.EXTINGUISHER, 790.0f, 640.0f, "procurement", "")
            asset(AssetType.AIRCON, 183.0f, 690.0f, "storage", "")
            asset(AssetType.FIRE_ALARM, 180.0f, 300.0f, "storage", "")
            asset(AssetType.CCTV, 100.0f, 100.0f, "storage", "")
            asset(AssetType.EXTINGUISHER, 270.0f, 640.0f, "storage", "")
            asset(AssetType.FIRE_LIGHT, 180.0f, 500.0f, "storage", "Emergency light · Storage Room")
            asset(AssetType.EXIT, 272.0f, 85.0f, "storage", "Exit (storage room, north door)")
            asset(AssetType.FIRE_ALARM, 500.0f, 120.0f, "sto", "")
            asset(AssetType.EXTINGUISHER, 530.0f, 280.0f, "sto", "")
            asset(AssetType.FIRE_ALARM, 690.0f, 150.0f, "unnamed_w", "")
            asset(AssetType.FIRE_LIGHT, 700.0f, 250.0f, "unnamed_w", "Emergency light · Room (name not shown)")
            asset(AssetType.EXIT, 790.0f, 90.0f, "unnamed_w", "Exit (north-east door of the room)")
            asset(AssetType.EXTINGUISHER, 940.0f, 420.0f, "pantry", "")
            asset(AssetType.FIRE_ALARM, 890.0f, 430.0f, "pantry", "")
            asset(AssetType.CCTV, 1000.0f, 335.0f, null, "CCTV camera (hallway, middle)")
            asset(AssetType.CCTV, 1500.0f, 335.0f, null, "CCTV camera (hallway, east)")
            asset(AssetType.WIFI, 1190.0f, 335.0f, null, "Wi-Fi access point (hallway)")
            asset(AssetType.FIRE_ALARM, 1100.0f, 335.0f, null, "Fire alarm (hallway)")
            asset(AssetType.FIRE_LIGHT, 700.0f, 340.0f, null, "Emergency light (hallway, west)")
            asset(AssetType.FIRE_LIGHT, 1300.0f, 335.0f, null, "Emergency light (hallway, east)")
            asset(AssetType.EXTINGUISHER, 840.0f, 345.0f, null, "Fire extinguisher (hallway, west)")
            asset(AssetType.EXTINGUISHER, 1450.0f, 330.0f, null, "Fire extinguisher (hallway, east)")
            asset(AssetType.EXIT, 1600.0f, 765.0f, "entrance", "Main entrance (exit)")
            asset(AssetType.CCTV, 1500.0f, 750.0f, "entrance", "")
            asset(AssetType.FIRE_LIGHT, 1700.0f, 740.0f, "entrance", "Emergency light · Entrance")
            asset(AssetType.EXTINGUISHER, 1420.0f, 745.0f, "entrance", "")
            asset(AssetType.EXIT, 1417.0f, 858.0f, null, "Exit (west ramp)")
            asset(AssetType.EXIT, 1752.0f, 858.0f, null, "Exit (east ramp)")
            asset(AssetType.HYDRANT, 1600.0f, 1130.0f, null, "Fire hydrant (outside, in front of the entrance - estimated)")
            asset(AssetType.HYDRANT, 170.0f, 965.0f, null, "Fire hydrant (outside, west end near the storage room - estimated)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId == 8 && floor == Floor.GROUND) ground else null
}