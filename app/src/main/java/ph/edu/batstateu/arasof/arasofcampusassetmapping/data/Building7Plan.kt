package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plan of building 7 (Joson Gymnasium, the yellow building): ground floor and 2nd floor.
 * Numbers are PIXELS of the plan picture (1920 x 1080). The ground floor is the left drawing and the
 * 2nd floor is the right drawing, each with its own frame.
 *
 * ASSETS (estimates: edit freely):
 *  - Fire extinguishers: 2 in the multipurpose court, 2 in the ground-floor backstage and 1 in the
 *    2nd-floor backstage. None anywhere else.
 *  - Big fans (8), speakers (6), microphones (4) and 1 microphone stand have NO icon on the plan
 *    (icon = false). They are still assets: tap the room to list them, tap one to report a problem.
 *  - The 2 basketball shooting boards (north and south end of the court) have an icon.
 */
object Building7Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(7, Floor.GROUND, "B7-1", 230.0f, 40.0f, 775.0f, 955.0f).apply {
            room("court", "Multipurpose Court (Yellow Building No. 7)", 437.0f, 228.0f, 797.0f, 752.0f, computers = 0)
            room("bench_nw", "Bench (northwest)", 303.0f, 122.0f, 587.0f, 425.0f, computers = 0, outline = listOf(303.0f to 122.0f, 587.0f to 122.0f, 587.0f to 184.0f, 390.0f to 186.0f, 390.0f to 425.0f, 303.0f to 425.0f))
            room("bench_ne", "Bench (northeast)", 642.0f, 120.0f, 935.0f, 425.0f, computers = 0, outline = listOf(647.0f to 120.0f, 935.0f to 120.0f, 935.0f to 425.0f, 848.0f to 425.0f, 848.0f to 186.0f, 647.0f to 184.0f))
            room("bench_sw", "Bench (southwest)", 303.0f, 490.0f, 388.0f, 800.0f, computers = 0)
            room("bench_se", "Bench (southeast)", 848.0f, 482.0f, 935.0f, 795.0f, computers = 0)
            room("stage", "Stage", 484.0f, 803.0f, 751.0f, 898.0f, computers = 0)
            room("backstage", "Backstage", 390.0f, 840.0f, 848.0f, 976.0f, computers = 0, outline = listOf(390.0f to 840.0f, 484.0f to 840.0f, 484.0f to 898.0f, 751.0f to 898.0f, 751.0f to 840.0f, 848.0f to 840.0f, 848.0f to 976.0f, 390.0f to 976.0f))
            zone("Walkway (west)", ZoneKind.CORRIDOR, 392.0f, 188.0f, 436.0f, 775.0f)
            zone("Walkway (east)", ZoneKind.CORRIDOR, 800.0f, 188.0f, 846.0f, 775.0f)
            zone("Walkway (south)", ZoneKind.CORRIDOR, 436.0f, 755.0f, 800.0f, 800.0f)
            zone("Entrance / Exit (north)", ZoneKind.PLAIN, 596.0f, 42.0f, 640.0f, 200.0f)
            zone("Entrance / Exit (west)", ZoneKind.PLAIN, 237.0f, 436.0f, 392.0f, 485.0f)
            zone("Entrance / Exit (east)", ZoneKind.PLAIN, 846.0f, 430.0f, 997.0f, 477.0f)
            zone("Entrance / Exit (southwest)", ZoneKind.PLAIN, 358.0f, 808.0f, 405.0f, 832.0f)
            zone("Entrance / Exit (southeast)", ZoneKind.PLAIN, 830.0f, 805.0f, 875.0f, 828.0f)
            zone("Entrance / Exit (backstage west)", ZoneKind.PLAIN, 397.0f, 972.0f, 436.0f, 990.0f)
            zone("Entrance / Exit (backstage east)", ZoneKind.PLAIN, 798.0f, 972.0f, 836.0f, 990.0f)
            zone("Stairs to 2nd floor", ZoneKind.STAIRS, 398.0f, 848.0f, 436.0f, 878.0f)
            zone("Stairs to 2nd floor", ZoneKind.STAIRS, 800.0f, 848.0f, 838.0f, 878.0f)
            zone("Stairs to stage", ZoneKind.STAIRS, 459.0f, 848.0f, 497.0f, 886.0f)
            zone("Stairs to stage", ZoneKind.STAIRS, 740.0f, 850.0f, 779.0f, 888.0f)
            asset(AssetType.BASKETBALL_BOARD, 616.0f, 252.0f, "court", "Basketball shooting board (north) · Multipurpose Court")
            asset(AssetType.BASKETBALL_BOARD, 616.0f, 716.0f, "court", "Basketball shooting board (south) · Multipurpose Court")
            asset(AssetType.EXTINGUISHER, 452.0f, 640.0f, "court", "Fire extinguisher (west) · Multipurpose Court")
            asset(AssetType.EXTINGUISHER, 782.0f, 380.0f, "court", "Fire extinguisher (east) · Multipurpose Court")
            asset(AssetType.EXTINGUISHER, 437.0f, 930.0f, "backstage", "Fire extinguisher (west) · Backstage")
            asset(AssetType.EXTINGUISHER, 800.0f, 930.0f, "backstage", "Fire extinguisher (east) · Backstage")
            asset(AssetType.FAN, 520.0f, 300.0f, "court", "Big fan 1 · Multipurpose Court", icon = false)
            asset(AssetType.FAN, 715.0f, 300.0f, "court", "Big fan 2 · Multipurpose Court", icon = false)
            asset(AssetType.FAN, 520.0f, 420.0f, "court", "Big fan 3 · Multipurpose Court", icon = false)
            asset(AssetType.FAN, 715.0f, 420.0f, "court", "Big fan 4 · Multipurpose Court", icon = false)
            asset(AssetType.FAN, 520.0f, 590.0f, "court", "Big fan 5 · Multipurpose Court", icon = false)
            asset(AssetType.FAN, 715.0f, 590.0f, "court", "Big fan 6 · Multipurpose Court", icon = false)
            asset(AssetType.FAN, 520.0f, 700.0f, "court", "Big fan 7 · Multipurpose Court", icon = false)
            asset(AssetType.FAN, 715.0f, 700.0f, "court", "Big fan 8 · Multipurpose Court", icon = false)
            asset(AssetType.SPEAKER, 455.0f, 300.0f, "court", "Speaker 1 · Multipurpose Court", icon = false)
            asset(AssetType.SPEAKER, 455.0f, 500.0f, "court", "Speaker 2 · Multipurpose Court", icon = false)
            asset(AssetType.SPEAKER, 455.0f, 700.0f, "court", "Speaker 3 · Multipurpose Court", icon = false)
            asset(AssetType.SPEAKER, 785.0f, 300.0f, "court", "Speaker 4 · Multipurpose Court", icon = false)
            asset(AssetType.SPEAKER, 785.0f, 500.0f, "court", "Speaker 5 · Multipurpose Court", icon = false)
            asset(AssetType.SPEAKER, 785.0f, 700.0f, "court", "Speaker 6 · Multipurpose Court", icon = false)
            asset(AssetType.MICROPHONE, 560.0f, 850.0f, "stage", "Microphone 1 · Stage", icon = false)
            asset(AssetType.MICROPHONE, 600.0f, 850.0f, "stage", "Microphone 2 · Stage", icon = false)
            asset(AssetType.MICROPHONE, 640.0f, 850.0f, "stage", "Microphone 3 · Stage", icon = false)
            asset(AssetType.MICROPHONE, 680.0f, 850.0f, "stage", "Microphone 4 · Stage", icon = false)
            asset(AssetType.MIC_STAND, 716.0f, 850.0f, "stage", "Microphone stand · Stage", icon = false)
        }.build(note = "Fans, speakers and microphones have no icons on the plan. Tap a room (Multipurpose Court, Stage) to see them and report a problem. Fan, speaker and microphone positions are estimates.")
    }

    private val second: FloorData by lazy {
        PlanBuilder(7, Floor.SECOND, "B7-2", 1095.0f, 785.0f, 490.0f, 210.0f).apply {
            room("control", "Gymnasium Control Room (2nd floor)", 1208.0f, 800.0f, 1470.0f, 895.0f, computers = 0)
            room("backstage2", "2nd Floor Backstage", 1111.0f, 838.0f, 1572.0f, 978.0f, computers = 0, outline = listOf(1111.0f to 838.0f, 1208.0f to 838.0f, 1208.0f to 895.0f, 1470.0f to 895.0f, 1470.0f to 838.0f, 1572.0f to 838.0f, 1572.0f to 978.0f, 1111.0f to 978.0f))
            zone("Stairs to backstage (1st floor)", ZoneKind.STAIRS, 1120.0f, 848.0f, 1163.0f, 878.0f)
            zone("Stairs to backstage (1st floor)", ZoneKind.STAIRS, 1521.0f, 848.0f, 1564.0f, 878.0f)
            asset(AssetType.EXTINGUISHER, 1340.0f, 930.0f, "backstage2", "Fire extinguisher · 2nd Floor Backstage")
        }.build(note = "The gymnasium control room is used by the emcee and for background music during events.")
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 7) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            else -> null
        }
    }
}