package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 19 (Lian Cafeteria, blue building): ground floor only.
 * Numbers are PIXELS of the plan picture (1456 x 819). Assets are estimates: edit freely.
 *
 * ASSETS (only these, no air conditioners):
 *  - Table with seats: 34 sets, placed on the table blocks of the plan (6 + 8 + 12 + 8).
 *  - Fire extinguisher: 1 in every stall (all 8 stalls are identical) and 1 in each of the 4 table areas.
 *  - Fire exit: 1 at each of the 5 entrance / exit points.
 *  - The glass railings in the plan legend are not an asset type, so they are not listed.
 */
object Building19Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(19, Floor.GROUND, "B19-1", 55.0f, 120.0f, 1275.0f, 585.0f).apply {
            room("cr_f", "Female CR", 88.0f, 157.0f, 212.0f, 268.0f, computers = 0)
            room("cr_m", "Male CR", 88.0f, 268.0f, 212.0f, 378.0f, computers = 0)
            room("stall1", "Stall 1", 540.0f, 220.0f, 622.0f, 402.0f, computers = 0)
            room("stall2", "Stall 2", 622.0f, 220.0f, 704.0f, 402.0f, computers = 0)
            room("stall3", "Stall 3", 704.0f, 220.0f, 794.0f, 402.0f, computers = 0)
            room("stall4", "Stall 4", 794.0f, 220.0f, 897.0f, 402.0f, computers = 0)
            room("stall5", "Stall 5", 897.0f, 220.0f, 1002.0f, 402.0f, computers = 0)
            room("stall6", "Stall 6", 1002.0f, 220.0f, 1085.0f, 402.0f, computers = 0)
            room("stall7", "Stall 7", 1085.0f, 220.0f, 1167.0f, 402.0f, computers = 0)
            room("stall8", "Stall 8", 1167.0f, 220.0f, 1255.0f, 402.0f, computers = 0)
            room("tables_nw", "Hallway · northwest tables", 270.0f, 225.0f, 535.0f, 395.0f, computers = 0)
            room("tables_w", "Hallway · west tables", 132.0f, 440.0f, 405.0f, 628.0f, computers = 0)
            room("tables_m", "Hallway · middle tables", 470.0f, 440.0f, 892.0f, 628.0f, computers = 0)
            room("tables_e", "Hallway · east tables", 962.0f, 440.0f, 1255.0f, 628.0f, computers = 0)
            room("exit_n", "Entrance / Exit (stall owners only)", 858.0f, 128.0f, 945.0f, 175.0f, computers = 0)
            room("exit_s1", "Entrance / Exit · Stairs (south 1)", 405.0f, 640.0f, 478.0f, 690.0f, computers = 0)
            room("exit_s2", "Entrance / Exit · Stairs (south 2)", 700.0f, 645.0f, 775.0f, 693.0f, computers = 0)
            room("exit_s3", "Entrance / Exit · Stairs (south 3)", 995.0f, 645.0f, 1068.0f, 693.0f, computers = 0)
            room("exit_e", "Entrance / Exit · Stairs (east)", 1262.0f, 470.0f, 1315.0f, 555.0f, computers = 0)
            zone("Hallway", ZoneKind.CORRIDOR, 212.0f, 157.0f, 262.0f, 342.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 550.0f, 157.0f, 1250.0f, 218.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 132.0f, 405.0f, 1255.0f, 442.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 405.0f, 442.0f, 470.0f, 628.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 892.0f, 442.0f, 962.0f, 628.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 800.0f, 645.0f, 990.0f, 688.0f)
            zone("Ramp", ZoneKind.RAMP, 60.0f, 385.0f, 130.0f, 628.0f)
            asset(AssetType.TABLE_SET, 316.0f, 263.0f, "tables_nw", "Table with seats 1 · Hallway · northwest tables")
            asset(AssetType.TABLE_SET, 403.0f, 263.0f, "tables_nw", "Table with seats 2 · Hallway · northwest tables")
            asset(AssetType.TABLE_SET, 495.0f, 263.0f, "tables_nw", "Table with seats 3 · Hallway · northwest tables")
            asset(AssetType.TABLE_SET, 316.0f, 352.0f, "tables_nw", "Table with seats 4 · Hallway · northwest tables")
            asset(AssetType.TABLE_SET, 403.0f, 352.0f, "tables_nw", "Table with seats 5 · Hallway · northwest tables")
            asset(AssetType.TABLE_SET, 495.0f, 352.0f, "tables_nw", "Table with seats 6 · Hallway · northwest tables")
            asset(AssetType.TABLE_SET, 168.0f, 485.0f, "tables_w", "Table with seats 1 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 236.0f, 485.0f, "tables_w", "Table with seats 2 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 310.0f, 485.0f, "tables_w", "Table with seats 3 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 375.0f, 485.0f, "tables_w", "Table with seats 4 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 168.0f, 572.0f, "tables_w", "Table with seats 5 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 236.0f, 572.0f, "tables_w", "Table with seats 6 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 310.0f, 572.0f, "tables_w", "Table with seats 7 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 375.0f, 572.0f, "tables_w", "Table with seats 8 · Hallway · west tables")
            asset(AssetType.TABLE_SET, 507.0f, 485.0f, "tables_m", "Table with seats 1 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 582.0f, 485.0f, "tables_m", "Table with seats 2 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 652.0f, 485.0f, "tables_m", "Table with seats 3 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 722.0f, 485.0f, "tables_m", "Table with seats 4 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 792.0f, 485.0f, "tables_m", "Table with seats 5 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 865.0f, 485.0f, "tables_m", "Table with seats 6 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 507.0f, 572.0f, "tables_m", "Table with seats 7 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 582.0f, 572.0f, "tables_m", "Table with seats 8 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 652.0f, 572.0f, "tables_m", "Table with seats 9 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 722.0f, 572.0f, "tables_m", "Table with seats 10 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 792.0f, 572.0f, "tables_m", "Table with seats 11 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 865.0f, 572.0f, "tables_m", "Table with seats 12 · Hallway · middle tables")
            asset(AssetType.TABLE_SET, 1005.0f, 485.0f, "tables_e", "Table with seats 1 · Hallway · east tables")
            asset(AssetType.TABLE_SET, 1075.0f, 485.0f, "tables_e", "Table with seats 2 · Hallway · east tables")
            asset(AssetType.TABLE_SET, 1140.0f, 485.0f, "tables_e", "Table with seats 3 · Hallway · east tables")
            asset(AssetType.TABLE_SET, 1210.0f, 485.0f, "tables_e", "Table with seats 4 · Hallway · east tables")
            asset(AssetType.TABLE_SET, 1005.0f, 572.0f, "tables_e", "Table with seats 5 · Hallway · east tables")
            asset(AssetType.TABLE_SET, 1075.0f, 572.0f, "tables_e", "Table with seats 6 · Hallway · east tables")
            asset(AssetType.TABLE_SET, 1140.0f, 572.0f, "tables_e", "Table with seats 7 · Hallway · east tables")
            asset(AssetType.TABLE_SET, 1210.0f, 572.0f, "tables_e", "Table with seats 8 · Hallway · east tables")
            asset(AssetType.EXTINGUISHER, 581.0f, 380.0f, "stall1", "Fire extinguisher · Stall 1")
            asset(AssetType.EXTINGUISHER, 663.0f, 380.0f, "stall2", "Fire extinguisher · Stall 2")
            asset(AssetType.EXTINGUISHER, 749.0f, 380.0f, "stall3", "Fire extinguisher · Stall 3")
            asset(AssetType.EXTINGUISHER, 845.0f, 380.0f, "stall4", "Fire extinguisher · Stall 4")
            asset(AssetType.EXTINGUISHER, 949.0f, 380.0f, "stall5", "Fire extinguisher · Stall 5")
            asset(AssetType.EXTINGUISHER, 1043.0f, 380.0f, "stall6", "Fire extinguisher · Stall 6")
            asset(AssetType.EXTINGUISHER, 1126.0f, 380.0f, "stall7", "Fire extinguisher · Stall 7")
            asset(AssetType.EXTINGUISHER, 1211.0f, 380.0f, "stall8", "Fire extinguisher · Stall 8")
            asset(AssetType.EXTINGUISHER, 282.0f, 385.0f, "tables_nw", "Fire extinguisher · Hallway · northwest tables")
            asset(AssetType.EXTINGUISHER, 142.0f, 618.0f, "tables_w", "Fire extinguisher · Hallway · west tables")
            asset(AssetType.EXTINGUISHER, 480.0f, 618.0f, "tables_m", "Fire extinguisher · Hallway · middle tables")
            asset(AssetType.EXTINGUISHER, 1245.0f, 618.0f, "tables_e", "Fire extinguisher · Hallway · east tables")
            asset(AssetType.EXIT, 901.0f, 152.0f, "exit_n", "Fire exit · Entrance / Exit (stall owners only)")
            asset(AssetType.EXIT, 441.0f, 665.0f, "exit_s1", "Fire exit · Entrance / Exit · Stairs (south 1)")
            asset(AssetType.EXIT, 737.0f, 669.0f, "exit_s2", "Fire exit · Entrance / Exit · Stairs (south 2)")
            asset(AssetType.EXIT, 1031.0f, 669.0f, "exit_s3", "Fire exit · Entrance / Exit · Stairs (south 3)")
            asset(AssetType.EXIT, 1288.0f, 512.0f, "exit_e", "Fire exit · Entrance / Exit · Stairs (east)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? =
        if (buildingId == 19 && floor == Floor.GROUND) ground else null
}