package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 5 (Apacible Museum, orange building): ground floor and 2nd floor.
 * Numbers are PIXELS of the plan picture (1920 x 1080). The left drawing ("1st floor" on the picture) is
 * the app's GROUND floor and the right drawing ("2nd floor") is the SECOND floor, each with its own frame.
 * Positions are estimates read off the drawing: edit freely.
 *
 * GROUND floor
 *  - Registrar: 2 air conditioners, 1 Wi-Fi, 4 computers.
 *  - The 4 other rooms are still "Unknown Room": 1 air conditioner each, no Wi-Fi.
 *
 * 2ND floor
 *  - General Education Office: about 10 computers, 1 Wi-Fi. (No air conditioner was given: none added.)
 *  - The east "Unknown Room" follows the same rule as the ground-floor unknown rooms: 1 air conditioner,
 *    no Wi-Fi (assumed, please confirm).
 *
 * SAFETY (both floors)
 *  - Every room: 1 fire extinguisher close to its door and 1 fire detector (smoke detector).
 *  - Every hallway: 1 fire extinguisher.
 *  - Every staircase: 1 fire exit light.
 *
 * Icons: extinguishers, detectors, exit lights and Wi-Fi have icons. Air conditioners and computers have
 * NO icon (icon = false) but are listed when the room is tapped.
 */
object Building5Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(5, Floor.GROUND, "B5-1", 106.0f, 226.0f, 720.0f, 406.0f).apply {
            room("unk1", "Unknown Room 1", 123.0f, 242.0f, 220.0f, 500.0f, computers = 0)
            room("unk2", "Unknown Room 2", 237.0f, 281.0f, 449.0f, 443.0f, computers = 0)
            room("registrar", "Registrar", 467.0f, 281.0f, 682.0f, 443.0f, computers = 4)
            room("unk4", "Unknown Room 4", 701.0f, 281.0f, 810.0f, 614.0f, computers = 0)
            room("unk3", "Unknown Room 3", 123.0f, 517.0f, 356.0f, 614.0f, computers = 0)

            zone("Hallway", ZoneKind.CORRIDOR, 237.0f, 459.0f, 468.0f, 501.0f)
            zone("1st Floor Hallway", ZoneKind.CORRIDOR, 468.0f, 459.0f, 684.0f, 614.0f)
            zone("Stairs", ZoneKind.STAIRS, 372.0f, 516.0f, 468.0f, 614.0f)
            // doors
            zone("Door", ZoneKind.PLAIN, 222.0f, 473.0f, 236.0f, 489.0f)
            zone("Door", ZoneKind.PLAIN, 247.0f, 444.0f, 269.0f, 458.0f)
            zone("Door", ZoneKind.PLAIN, 240.0f, 502.0f, 282.0f, 516.0f)
            zone("Door", ZoneKind.PLAIN, 481.0f, 444.0f, 493.0f, 458.0f)
            zone("Registrar Window", ZoneKind.PLAIN, 528.0f, 442.0f, 651.0f, 458.0f)
            zone("Door", ZoneKind.PLAIN, 683.0f, 501.0f, 700.0f, 527.0f)
            zone("Entrance / Exit", ZoneKind.PLAIN, 530.0f, 616.0f, 613.0f, 630.0f)

            // ---- Registrar: 2 air conditioners, 1 Wi-Fi, 4 computers
            asset(AssetType.AIRCON, 495.0f, 296.0f, "registrar", "Air conditioner 1 · Registrar", icon = false)
            asset(AssetType.AIRCON, 655.0f, 296.0f, "registrar", "Air conditioner 2 · Registrar", icon = false)
            asset(AssetType.WIFI, 575.0f, 300.0f, "registrar", "Wi-Fi access point · Registrar")
            asset(AssetType.COMPUTER, 520.0f, 415.0f, "registrar", "Computer 1 · Registrar", icon = false)
            asset(AssetType.COMPUTER, 560.0f, 415.0f, "registrar", "Computer 2 · Registrar", icon = false)
            asset(AssetType.COMPUTER, 620.0f, 415.0f, "registrar", "Computer 3 · Registrar", icon = false)
            asset(AssetType.COMPUTER, 660.0f, 415.0f, "registrar", "Computer 4 · Registrar", icon = false)

            // ---- the other rooms: 1 air conditioner each, no Wi-Fi
            asset(AssetType.AIRCON, 150.0f, 275.0f, "unk1", "", icon = false)
            asset(AssetType.AIRCON, 300.0f, 300.0f, "unk2", "", icon = false)
            asset(AssetType.AIRCON, 755.0f, 320.0f, "unk4", "", icon = false)
            asset(AssetType.AIRCON, 160.0f, 545.0f, "unk3", "", icon = false)

            // ---- fire detector in every room
            asset(AssetType.FIRE_ALARM, 195.0f, 275.0f, "unk1", "Fire detector · Unknown Room 1")
            asset(AssetType.FIRE_ALARM, 390.0f, 300.0f, "unk2", "Fire detector · Unknown Room 2")
            asset(AssetType.FIRE_ALARM, 575.0f, 335.0f, "registrar", "Fire detector · Registrar")
            asset(AssetType.FIRE_ALARM, 755.0f, 360.0f, "unk4", "Fire detector · Unknown Room 4")
            asset(AssetType.FIRE_ALARM, 240.0f, 545.0f, "unk3", "Fire detector · Unknown Room 3")

            // ---- fire extinguisher close to the door of every room
            asset(AssetType.EXTINGUISHER, 207.0f, 470.0f, "unk1", "Fire extinguisher (by the door) · Unknown Room 1")
            asset(AssetType.EXTINGUISHER, 280.0f, 430.0f, "unk2", "Fire extinguisher (by the door) · Unknown Room 2")
            asset(AssetType.EXTINGUISHER, 503.0f, 430.0f, "registrar", "Fire extinguisher (by the door) · Registrar")
            asset(AssetType.EXTINGUISHER, 717.0f, 545.0f, "unk4", "Fire extinguisher (by the door) · Unknown Room 4")
            asset(AssetType.EXTINGUISHER, 283.0f, 530.0f, "unk3", "Fire extinguisher (by the door) · Unknown Room 3")

            // ---- hallways: 1 fire extinguisher each
            asset(AssetType.EXTINGUISHER, 425.0f, 480.0f, null, "Fire extinguisher · Hallway")
            asset(AssetType.EXTINGUISHER, 612.0f, 598.0f, null, "Fire extinguisher · 1st Floor Hallway")

            // ---- stairs: fire exit light
            asset(AssetType.FIRE_LIGHT, 420.0f, 540.0f, null, "Fire exit light · Stairs (ground floor)")
        }.build()
    }

    private val second: FloorData by lazy {
        PlanBuilder(5, Floor.SECOND, "B5-2", 1003.0f, 239.0f, 720.0f, 450.0f).apply {
            // L-shaped office: the narrow part at the top-left sticks out, the lower-left part wraps the stairs
            room(
                "geo", "General Education Office", 1020.0f, 256.0f, 1579.0f, 625.0f, computers = 10,
                outline = listOf(
                    1020.0f to 256.0f, 1117.0f to 256.0f, 1117.0f to 292.0f, 1579.0f to 292.0f,
                    1579.0f to 475.0f, 1352.0f to 475.0f, 1352.0f to 514.0f, 1255.0f to 514.0f,
                    1255.0f to 625.0f, 1020.0f to 625.0f
                )
            )
            room("unk5", "Unknown Room 5", 1599.0f, 292.0f, 1707.0f, 626.0f, computers = 0)

            zone("2nd Floor Hallway", ZoneKind.CORRIDOR, 1369.0f, 491.0f, 1583.0f, 626.0f)
            zone("Stairs", ZoneKind.STAIRS, 1270.0f, 529.0f, 1360.0f, 626.0f)
            zone("Balcony", ZoneKind.PLAIN, 1373.0f, 641.0f, 1589.0f, 673.0f)
            // doors
            zone("Door", ZoneKind.PLAIN, 1372.0f, 476.0f, 1432.0f, 490.0f)
            zone("Door", ZoneKind.PLAIN, 1583.0f, 503.0f, 1598.0f, 528.0f)
            zone("Balcony Entrance", ZoneKind.PLAIN, 1385.0f, 628.0f, 1571.0f, 641.0f)

            // ---- General Education Office: about 10 computers, 1 Wi-Fi
            val xs = listOf(1410.0f, 1445.0f, 1480.0f, 1515.0f, 1550.0f)
            var pc = 0
            for (y in listOf(350.0f, 410.0f)) for (x in xs) {
                pc++
                asset(AssetType.COMPUTER, x, y, "geo", "Computer $pc · General Education Office", icon = false)
            }
            asset(AssetType.WIFI, 1480.0f, 312.0f, "geo", "Wi-Fi access point · General Education Office")
            asset(AssetType.FIRE_ALARM, 1300.0f, 305.0f, "geo", "Fire detector · General Education Office")
            asset(AssetType.EXTINGUISHER, 1417.0f, 460.0f, "geo", "Fire extinguisher (by the door) · General Education Office")

            // ---- Unknown Room 5: 1 air conditioner (assumed), no Wi-Fi
            asset(AssetType.AIRCON, 1625.0f, 325.0f, "unk5", "", icon = false)
            asset(AssetType.FIRE_ALARM, 1680.0f, 325.0f, "unk5", "Fire detector · Unknown Room 5")
            asset(AssetType.EXTINGUISHER, 1620.0f, 545.0f, "unk5", "Fire extinguisher (by the door) · Unknown Room 5")

            // ---- hallway and stairs
            asset(AssetType.EXTINGUISHER, 1385.0f, 602.0f, null, "Fire extinguisher · 2nd Floor Hallway")
            asset(AssetType.FIRE_LIGHT, 1315.0f, 565.0f, null, "Fire exit light · Stairs (2nd floor)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 5) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            else -> null
        }
    }
}