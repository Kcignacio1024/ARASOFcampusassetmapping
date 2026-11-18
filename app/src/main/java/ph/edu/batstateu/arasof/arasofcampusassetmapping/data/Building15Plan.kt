package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plans of building 15 (Calatagan Building), traced from the architectural drawings:
 *   Ground floor, 2nd floor, and the 3rd floor (rooftop, no rooms).
 *
 * All numbers below are PIXELS of the drawing pictures (1920 x 1080); they are converted to 0..1
 * fractions of one shared frame, so every floor has the same outer box.
 * To fix a room or asset position, just change its numbers.
 *
 * Assets are estimates read from the room names / symbols on the plan (only the big things).
 * Edit / add / remove lines as needed.
 */
object Building15Plan {
    // shared frame (same size for every floor)
    private const val X0 = 10.0f
    private const val Y0 = 40.0f
    private const val PW = 1905.0f
    private const val PH = 925.0f
    const val ASPECT = PW / PH

    private fun fx(px: Float) = (px - X0) / PW
    private fun fy(py: Float) = (py - Y0) / PH

    private class Builder(private val floor: Floor, private val prefix: String) {
        val rooms = mutableListOf<Room>()
        val zones = mutableListOf<Zone>()
        val assets = mutableListOf<Asset>()
        private var n = 0

        fun room(
            key: String, name: String, l: Float, t: Float, r: Float, b: Float,
            computers: Int = 0, outline: List<Pair<Float, Float>> = emptyList()
        ) {
            rooms += Room(
                "$prefix-$key", 15, floor, name, fx(l), fy(t), (r - l) / PW, (b - t) / PH, computers,
                outline.map { fx(it.first) to fy(it.second) }
            )
        }

        fun zone(name: String, kind: ZoneKind, l: Float, t: Float, r: Float, b: Float) {
            zones += Zone(name, kind, fx(l), fy(t), (r - l) / PW, (b - t) / PH)
        }

        fun asset(type: AssetType, px: Float, py: Float, roomKey: String?, detail: String = "") {
            val roomName = rooms.firstOrNull { it.id == "$prefix-$roomKey" }?.name
            val label = detail.ifBlank { "${type.label} · $roomName" }
            n++
            assets += Asset("$prefix-A$n", 15, floor, type, fx(px), fy(py), label, roomKey?.let { "$prefix-$it" })
        }

        fun build(note: String? = null) = FloorData(rooms.toList(), assets.toList(), zones.toList(), ASPECT, note)
    }

    // ------------------------------------------------------------------ GROUND FLOOR
    private val ground: FloorData by lazy {
        Builder(Floor.GROUND, "B15-1").apply {
            room("toilet_male", "Toilet (Male)", 75.0f, 168.0f, 270.0f, 345.0f, computers = 0)
            room("toilet_pwd", "Toilet (PWD)", 275.0f, 168.0f, 348.0f, 272.0f, computers = 0)
            room("toilet_female", "Toilet (Female)", 75.0f, 348.0f, 268.0f, 540.0f, computers = 0)
            room("moot", "Moot Court", 352.0f, 165.0f, 895.0f, 540.0f, computers = 0, outline = listOf(445.0f to 165.0f, 805.0f to 165.0f, 805.0f to 272.0f, 895.0f to 272.0f, 895.0f to 540.0f, 352.0f to 540.0f, 352.0f to 272.0f, 445.0f to 272.0f))
            room("judge", "Judge's Room", 357.0f, 168.0f, 445.0f, 272.0f, computers = 0)
            room("electrical", "Electrical Room", 805.0f, 165.0f, 972.0f, 362.0f, computers = 0, outline = listOf(805.0f to 165.0f, 972.0f to 165.0f, 972.0f to 362.0f, 895.0f to 362.0f, 895.0f to 272.0f, 805.0f to 272.0f))
            room("psych", "Psychological Assessment & Testing Room", 980.0f, 165.0f, 1500.0f, 540.0f, computers = 0)
            room("lab", "Experimental / Psychophysiological Lab", 1508.0f, 165.0f, 1880.0f, 540.0f, computers = 0)
            room("faculty", "Faculty Room", 1695.0f, 610.0f, 1890.0f, 818.0f, computers = 4)
            room("deans", "Dean's Office", 1505.0f, 678.0f, 1690.0f, 915.0f, computers = 0)
            room("unnamed", "Room (name not shown on plan)", 1695.0f, 820.0f, 1890.0f, 915.0f, computers = 0)
            zone("Hallway", ZoneKind.CORRIDOR, 272.0f, 275.0f, 352.0f, 540.0f)
            zone("Corridor", ZoneKind.CORRIDOR, 62.0f, 540.0f, 1890.0f, 618.0f)
            zone("Stairs up to 2nd floor", ZoneKind.STAIRS, 897.0f, 368.0f, 972.0f, 540.0f)
            zone("Fire exit steel stair", ZoneKind.STAIRS, 1340.0f, 70.0f, 1690.0f, 155.0f)
            asset(AssetType.AIRCON, 1008.0f, 192.0f, "psych", "Air conditioner (west)")
            asset(AssetType.AIRCON, 1473.0f, 192.0f, "psych", "Air conditioner (east)")
            asset(AssetType.WIFI, 1240.0f, 192.0f, "psych", "")
            asset(AssetType.CCTV, 1005.0f, 520.0f, "psych", "")
            asset(AssetType.FIRE_ALARM, 1240.0f, 520.0f, "psych", "")
            asset(AssetType.PROJECTOR, 1025.0f, 235.0f, "psych", "")
            asset(AssetType.AIRCON, 1533.0f, 192.0f, "lab", "Air conditioner (west)")
            asset(AssetType.AIRCON, 1850.0f, 192.0f, "lab", "Air conditioner (east)")
            asset(AssetType.WIFI, 1700.0f, 235.0f, "lab", "")
            asset(AssetType.CCTV, 1860.0f, 520.0f, "lab", "")
            asset(AssetType.FIRE_ALARM, 1690.0f, 520.0f, "lab", "")
            asset(AssetType.SPECIAL_EQUIP, 1680.0f, 345.0f, "lab", "Psychophysiology lab equipment")
            asset(AssetType.PROJECTOR, 1855.0f, 260.0f, "lab", "")
            asset(AssetType.AIRCON, 480.0f, 205.0f, "moot", "Air conditioner (north-west)")
            asset(AssetType.AIRCON, 780.0f, 205.0f, "moot", "Air conditioner (north-east)")
            asset(AssetType.WIFI, 620.0f, 300.0f, "moot", "")
            asset(AssetType.CCTV, 385.0f, 520.0f, "moot", "")
            asset(AssetType.CCTV, 880.0f, 290.0f, "moot", "")
            asset(AssetType.FIRE_ALARM, 760.0f, 430.0f, "moot", "")
            asset(AssetType.EXTINGUISHER, 845.0f, 528.0f, "moot", "")
            asset(AssetType.FIRE_ALARM, 400.0f, 218.0f, "judge", "")
            asset(AssetType.SPECIAL_EQUIP, 930.0f, 320.0f, "electrical", "Electrical panel / main breaker")
            asset(AssetType.FIRE_ALARM, 850.0f, 205.0f, "electrical", "")
            asset(AssetType.EXTINGUISHER, 925.0f, 205.0f, "electrical", "")
            asset(AssetType.AIRCON, 1865.0f, 725.0f, "faculty", "")
            asset(AssetType.WIFI, 1790.0f, 640.0f, "faculty", "")
            asset(AssetType.FIRE_ALARM, 1790.0f, 790.0f, "faculty", "")
            asset(AssetType.COMPUTER, 1780.0f, 735.0f, "faculty", "Computers (about 4) · Faculty Room")
            asset(AssetType.CCTV, 1712.0f, 628.0f, "faculty", "")
            asset(AssetType.AIRCON, 1522.0f, 725.0f, "deans", "")
            asset(AssetType.WIFI, 1600.0f, 885.0f, "deans", "")
            asset(AssetType.FIRE_ALARM, 1600.0f, 720.0f, "deans", "")
            asset(AssetType.CCTV, 1522.0f, 900.0f, "deans", "")
            asset(AssetType.EXIT, 1845.0f, 830.0f, "unnamed", "Exit (rear, faculty wing)")
            asset(AssetType.FIRE_ALARM, 1780.0f, 885.0f, "unnamed", "")
            asset(AssetType.CCTV, 200.0f, 578.0f, null, "CCTV camera (corridor, west)")
            asset(AssetType.FIRE_LIGHT, 400.0f, 572.0f, null, "Emergency light (corridor, west)")
            asset(AssetType.EXTINGUISHER, 520.0f, 585.0f, null, "Fire extinguisher (corridor, west)")
            asset(AssetType.WIFI, 700.0f, 575.0f, null, "Wi-Fi access point (corridor)")
            asset(AssetType.CCTV, 1000.0f, 578.0f, null, "CCTV camera (corridor, middle)")
            asset(AssetType.FIRE_LIGHT, 1150.0f, 572.0f, null, "Emergency light (corridor, middle)")
            asset(AssetType.EXTINGUISHER, 1250.0f, 585.0f, null, "Fire extinguisher (corridor, east)")
            asset(AssetType.CCTV, 1600.0f, 578.0f, null, "CCTV camera (corridor, east)")
            asset(AssetType.FIRE_LIGHT, 1750.0f, 572.0f, null, "Emergency light (corridor, east)")
            asset(AssetType.EXIT, 1850.0f, 590.0f, null, "Exit (east end of corridor)")
            asset(AssetType.EXIT, 62.0f, 585.0f, null, "Exit (west end of corridor)")
            asset(AssetType.FIRE_ALARM, 312.0f, 420.0f, null, "Fire alarm (hallway)")
            asset(AssetType.FIRE_LIGHT, 935.0f, 450.0f, null, "Emergency light (main stairs)")
            asset(AssetType.EXIT, 1500.0f, 115.0f, null, "Fire exit steel stair")
            asset(AssetType.WIFI, 1615.0f, 145.0f, null, "Wi-Fi access point (near fire exit stair)")
            asset(AssetType.HYDRANT, 700.0f, 700.0f, null, "Fire hydrant (outside the building, estimated)")
        }.build()
    }

    // ------------------------------------------------------------------ 2ND FLOOR
    private val second: FloorData by lazy {
        Builder(Floor.SECOND, "B15-2").apply {
            room("multimedia", "Multimedia Room", 62.0f, 190.0f, 268.0f, 568.0f, computers = 26)
            room("archives", "Theses and Audio/Visual Archives", 272.0f, 275.0f, 462.0f, 568.0f, computers = 0)
            room("crimelab", "Crime Laboratory", 468.0f, 275.0f, 893.0f, 568.0f, computers = 0)
            room("lie", "Lie Detection Room", 983.0f, 272.0f, 1135.0f, 428.0f, computers = 0)
            room("crimescene", "Crime Scene Room", 983.0f, 430.0f, 1335.0f, 568.0f, computers = 0, outline = listOf(983.0f to 430.0f, 1188.0f to 430.0f, 1188.0f to 466.0f, 1335.0f to 466.0f, 1335.0f to 568.0f, 983.0f to 568.0f))
            room("dark", "Dark Room", 1188.0f, 272.0f, 1335.0f, 466.0f, computers = 0)
            room("radio", "Radio Laboratory", 1340.0f, 270.0f, 1522.0f, 455.0f, computers = 3)
            room("recording", "Recording Room", 1340.0f, 457.0f, 1522.0f, 568.0f, computers = 0)
            room("control", "Control/Editing Room (Production)", 1588.0f, 190.0f, 1900.0f, 375.0f, computers = 8)
            room("production", "Production Room", 1522.0f, 377.0f, 1900.0f, 785.0f, computers = 0)
            room("dressing", "Dressing Room", 1522.0f, 792.0f, 1700.0f, 950.0f, computers = 0)
            room("props", "Props / Storage Room", 1712.0f, 792.0f, 1900.0f, 950.0f, computers = 0)
            zone("Corridor", ZoneKind.CORRIDOR, 268.0f, 185.0f, 1590.0f, 272.0f)
            zone("Corridor", ZoneKind.CORRIDOR, 1522.0f, 185.0f, 1590.0f, 376.0f)
            zone("Stairs", ZoneKind.STAIRS, 895.0f, 270.0f, 980.0f, 562.0f)
            zone("Stairs down to G.F", ZoneKind.STAIRS, 888.0f, 570.0f, 990.0f, 668.0f)
            zone("Fire exit steel stair", ZoneKind.STAIRS, 105.0f, 100.0f, 462.0f, 190.0f)
            zone("Fire exit steel stair", ZoneKind.STAIRS, 995.0f, 100.0f, 1710.0f, 190.0f)
            zone("Corridor", ZoneKind.CORRIDOR, 1136.0f, 272.0f, 1187.0f, 430.0f)
            asset(AssetType.AIRCON, 78.0f, 250.0f, "multimedia", "Air conditioner (upper)")
            asset(AssetType.AIRCON, 78.0f, 445.0f, "multimedia", "Air conditioner (lower)")
            asset(AssetType.PROJECTOR, 165.0f, 212.0f, "multimedia", "")
            asset(AssetType.WIFI, 235.0f, 238.0f, "multimedia", "")
            asset(AssetType.CCTV, 240.0f, 545.0f, "multimedia", "")
            asset(AssetType.COMPUTER, 165.0f, 380.0f, "multimedia", "Computers (26 workstations) · Multimedia Room")
            asset(AssetType.FIRE_ALARM, 200.0f, 305.0f, "multimedia", "")
            asset(AssetType.AIRCON, 300.0f, 300.0f, "archives", "")
            asset(AssetType.WIFI, 432.0f, 300.0f, "archives", "")
            asset(AssetType.CCTV, 300.0f, 540.0f, "archives", "")
            asset(AssetType.FIRE_ALARM, 440.0f, 540.0f, "archives", "")
            asset(AssetType.AIRCON, 500.0f, 300.0f, "crimelab", "Air conditioner (west)")
            asset(AssetType.AIRCON, 860.0f, 300.0f, "crimelab", "Air conditioner (east)")
            asset(AssetType.WIFI, 680.0f, 300.0f, "crimelab", "")
            asset(AssetType.CCTV, 870.0f, 545.0f, "crimelab", "")
            asset(AssetType.FIRE_ALARM, 680.0f, 545.0f, "crimelab", "")
            asset(AssetType.FIRE_LIGHT, 560.0f, 545.0f, "crimelab", "Emergency light · Crime Laboratory")
            asset(AssetType.AIRCON, 1005.0f, 292.0f, "lie", "")
            asset(AssetType.FIRE_ALARM, 1118.0f, 292.0f, "lie", "")
            asset(AssetType.CCTV, 1118.0f, 412.0f, "lie", "")
            asset(AssetType.SPECIAL_EQUIP, 1060.0f, 372.0f, "lie", "Polygraph / lie detection equipment")
            asset(AssetType.AIRCON, 1010.0f, 452.0f, "crimescene", "")
            asset(AssetType.WIFI, 1100.0f, 550.0f, "crimescene", "")
            asset(AssetType.FIRE_ALARM, 1160.0f, 500.0f, "crimescene", "")
            asset(AssetType.CCTV, 1310.0f, 550.0f, "crimescene", "")
            asset(AssetType.FIRE_ALARM, 1310.0f, 292.0f, "dark", "")
            asset(AssetType.SPECIAL_EQUIP, 1260.0f, 400.0f, "dark", "Photo development equipment")
            asset(AssetType.AIRCON, 1362.0f, 292.0f, "radio", "")
            asset(AssetType.WIFI, 1500.0f, 292.0f, "radio", "")
            asset(AssetType.FIRE_ALARM, 1500.0f, 435.0f, "radio", "")
            asset(AssetType.SPECIAL_EQUIP, 1430.0f, 435.0f, "radio", "Broadcast / control console")
            asset(AssetType.COMPUTER, 1400.0f, 340.0f, "radio", "Computers (about 3) · Radio Laboratory")
            asset(AssetType.AIRCON, 1362.0f, 482.0f, "recording", "")
            asset(AssetType.FIRE_ALARM, 1500.0f, 482.0f, "recording", "")
            asset(AssetType.SPECIAL_EQUIP, 1430.0f, 540.0f, "recording", "Recording equipment")
            asset(AssetType.CCTV, 1500.0f, 550.0f, "recording", "")
            asset(AssetType.AIRCON, 1612.0f, 208.0f, "control", "")
            asset(AssetType.WIFI, 1740.0f, 208.0f, "control", "")
            asset(AssetType.COMPUTER, 1740.0f, 262.0f, "control", "Editing computers (about 8) · Control/Editing Room")
            asset(AssetType.SPECIAL_EQUIP, 1740.0f, 350.0f, "control", "Control console / video equipment")
            asset(AssetType.FIRE_ALARM, 1650.0f, 355.0f, "control", "")
            asset(AssetType.CCTV, 1880.0f, 210.0f, "control", "")
            asset(AssetType.AIRCON, 1545.0f, 405.0f, "production", "Air conditioner (west)")
            asset(AssetType.AIRCON, 1880.0f, 405.0f, "production", "Air conditioner (east)")
            asset(AssetType.WIFI, 1710.0f, 425.0f, "production", "")
            asset(AssetType.CCTV, 1880.0f, 770.0f, "production", "")
            asset(AssetType.FIRE_ALARM, 1710.0f, 520.0f, "production", "")
            asset(AssetType.SPECIAL_EQUIP, 1710.0f, 680.0f, "production", "Studio lighting & equipment")
            asset(AssetType.EXTINGUISHER, 1545.0f, 770.0f, "production", "")
            asset(AssetType.FIRE_LIGHT, 1880.0f, 600.0f, "production", "Emergency light · Production Room")
            asset(AssetType.FIRE_ALARM, 1680.0f, 812.0f, "dressing", "")
            asset(AssetType.FIRE_ALARM, 1880.0f, 812.0f, "props", "")
            asset(AssetType.CCTV, 1735.0f, 930.0f, "props", "")
            asset(AssetType.CCTV, 330.0f, 230.0f, null, "CCTV camera (corridor, west)")
            asset(AssetType.FIRE_LIGHT, 600.0f, 228.0f, null, "Emergency light (corridor, west)")
            asset(AssetType.EXTINGUISHER, 890.0f, 238.0f, null, "Fire extinguisher (near main stairs)")
            asset(AssetType.WIFI, 1000.0f, 228.0f, null, "Wi-Fi access point (corridor)")
            asset(AssetType.CCTV, 1150.0f, 230.0f, null, "CCTV camera (corridor, middle)")
            asset(AssetType.FIRE_LIGHT, 1250.0f, 228.0f, null, "Emergency light (corridor, east)")
            asset(AssetType.EXTINGUISHER, 1450.0f, 238.0f, null, "Fire extinguisher (corridor, east)")
            asset(AssetType.CCTV, 1505.0f, 230.0f, null, "CCTV camera (corridor, east end)")
            asset(AssetType.FIRE_LIGHT, 938.0f, 400.0f, null, "Emergency light (main stairs)")
            asset(AssetType.EXIT, 330.0f, 150.0f, null, "Fire exit steel stair (west)")
            asset(AssetType.EXIT, 1450.0f, 150.0f, null, "Fire exit steel stair (east)")
            asset(AssetType.EXIT, 938.0f, 625.0f, null, "Main stairs down to G.F")
        }.build()
    }

    // ------------------------------------------------------------------ 3RD FLOOR (rooftop)
    private val third: FloorData by lazy {
        Builder(Floor.THIRD, "B15-3").apply {
            zone("Rooftop", ZoneKind.ROOFTOP, X0, Y0, X0 + PW, Y0 + PH)
        }.build(note = "Rooftop: there are no rooms on this floor.")
    }

    /** Returns the real plan for this building/floor, or null for other buildings. */
    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 15) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            Floor.THIRD -> third
            Floor.FOURTH -> null
        }
    }
}