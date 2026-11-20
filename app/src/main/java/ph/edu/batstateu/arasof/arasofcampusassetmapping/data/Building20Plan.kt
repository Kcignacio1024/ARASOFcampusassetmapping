package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*
import kotlin.math.ceil
import kotlin.math.sqrt

/**
 * Building 20 (Nasugbu Hostel, orange building, mixed-use): ground, 2nd and 3rd floor.
 * The floors follow the sketch exactly (picture 1456 x 816): one square per floor, the stairs in the
 * north-east corner and in the center, and on the ground floor the road, the entrance stairs and the
 * north / south entrance-exit marks. There is NO real room layout yet, so each floor is ONE tappable area:
 * tap the floor to see its assets. No icons are drawn (icon = false).
 * Every number here is an estimate: edit freely.
 *
 *  - Ground floor: events and gatherings (sound system, stage, projection, seating, pantry, reception).
 *  - 2nd floor: meetings (conference / meeting equipment).
 *  - 3rd floor: accommodation (guest rooms 301-316, lounge & dining, laundry).
 *  - Fire extinguishers, smoke detectors and sprinklers: fixed at EXTINGUISHERS_PER_FLOOR /
 *    SMOKE_DETECTORS_PER_FLOOR / SPRINKLERS_PER_FLOOR (4 each) on every floor (change the constants below).
 */
object Building20Plan {

    /** Number of fire extinguishers on each floor of Building 20. */
    private const val EXTINGUISHERS_PER_FLOOR = 4

    /** Number of smoke detectors / fire alarms on each floor of Building 20. */
    private const val SMOKE_DETECTORS_PER_FLOOR = 4

    /** Number of fire sprinklers on each floor of Building 20. */
    private const val SPRINKLERS_PER_FLOOR = 4

    private const val NOTE =
        "Estimated layout (no floor plan yet): each floor is one area, as in the sketch. Each floor has " +
                "$EXTINGUISHERS_PER_FLOOR fire extinguishers, $SMOKE_DETECTORS_PER_FLOOR smoke detectors and $SPRINKLERS_PER_FLOOR sprinklers. Tap the floor to see its assets."

    private class Box(val x: Float, val y: Float, val w: Float, val h: Float)

    // the building square of each floor in the sketch (picture pixels)
    private val groundBox = Box(122f, 46f, 266f, 251f)
    private val groundFrame = Box(50f, 38f, 412f, 342f) // includes the road below the building
    private val secondBox = Box(1073f, 41f, 267f, 253f)
    private val thirdBox = Box(577f, 417f, 268f, 254f)

    /** Small helper: the area is given as fractions (u, v) of the building square, assets are spread inside it. */
    private class Layout(floor: Floor, prefix: String, private val box: Box, frame: Box = box) {
        private val pb = PlanBuilder(20, floor, prefix, frame.x, frame.y, frame.w, frame.h)

        private class R(val name: String, val u0: Float, val v0: Float, val u1: Float, val v1: Float)

        private val rooms = mutableMapOf<String, R>()

        private fun px(u: Float) = box.x + u * box.w
        private fun py(v: Float) = box.y + v * box.h

        fun room(key: String, name: String, u0: Float, v0: Float, u1: Float, v1: Float, computers: Int = 0) {
            rooms[key] = R(name, u0, v0, u1, v1)
            pb.room(key, name, px(u0), py(v0), px(u1), py(v1), computers = computers)
        }

        fun zone(name: String, kind: ZoneKind, u0: Float, v0: Float, u1: Float, v1: Float) =
            pb.zone(name, kind, px(u0), py(v0), px(u1), py(v1))

        /** zone given directly in picture pixels (for things outside the building square) */
        fun zonePx(name: String, kind: ZoneKind, l: Float, t: Float, r: Float, b: Float) =
            pb.zone(name, kind, l, t, r, b)

        /** The two stairs drawn on every floor of the sketch: north-east corner and the center. */
        fun stairs() {
            zone("Stairs", ZoneKind.STAIRS, 0.81f, 0.035f, 0.96f, 0.092f)
            zone("Stairs", ZoneKind.STAIRS, 0.42f, 0.52f, 0.57f, 0.585f)
        }

        /** One asset at a spot given as a fraction (0..1) of the area itself. */
        fun put(type: AssetType, key: String, fx: Float, fy: Float, label: String) {
            val r = rooms.getValue(key)
            pb.asset(type, px(r.u0 + (r.u1 - r.u0) * fx), py(r.v0 + (r.v1 - r.v0) * fy), key, label, icon = false)
        }

        /** [count] copies of one asset laid out in a grid inside the area. */
        fun spread(type: AssetType, key: String, count: Int, label: String = type.label) {
            if (count <= 0) return
            val r = rooms.getValue(key)
            val rw = (r.u1 - r.u0) * box.w
            val rh = (r.v1 - r.v0) * box.h
            val cols = ceil(sqrt(count * rw / rh)).toInt().coerceIn(1, count)
            val rows = (count + cols - 1) / cols
            for (i in 0 until count) {
                val fx = (i % cols + 0.5f) / cols
                val fy = (i / cols + 0.5f) / rows
                put(type, key, fx, fy, if (count > 1) "$label ${i + 1} · ${r.name}" else "$label · ${r.name}")
            }
        }

        /** A fixed number of extinguishers per floor. */
        fun extinguishers(key: String) {
            spread(AssetType.EXTINGUISHER, key, EXTINGUISHERS_PER_FLOOR, "Fire extinguisher")
        }

        fun safety(key: String, alarms: Int, sprinklers: Int, lights: Int) {
            extinguishers(key)
            spread(AssetType.FIRE_ALARM, key, alarms, "Smoke detector")
            spread(AssetType.SPRINKLER, key, sprinklers, "Fire sprinkler")
            spread(AssetType.FIRE_LIGHT, key, lights, "Emergency light")
        }

        /** Guest rooms in two rows: every room gets its own aircon and fan (smoke detectors are counted per floor). */
        fun guestRooms(key: String, firstNo: Int, count: Int) {
            val cols = (count + 1) / 2
            for (i in 0 until count) {
                val no = firstNo + i
                val fx = (i % cols + 0.5f) / cols
                val fy = if (i < cols) 0.30f else 0.75f
                put(AssetType.AIRCON, key, fx - 0.2f / cols, fy, "Air conditioner · Room $no")
                put(AssetType.FAN, key, fx + 0.2f / cols, fy, "Ceiling fan · Room $no")
            }
        }

        fun build() = pb.build(NOTE)
    }

    // ---------------------------------------------------------------- GROUND: events & gatherings
    private val ground: FloorData by lazy {
        Layout(Floor.GROUND, "B20-1", groundBox, groundFrame).apply {
            // outside the building square, as drawn
            zonePx("Road", ZoneKind.PLAIN, 58f, 297f, 455f, 373f)
            zonePx("Stairs (entrance)", ZoneKind.STAIRS, 235f, 306f, 274f, 321f)
            zonePx("Entrance / Exit (south)", ZoneKind.PLAIN, 240f, 291f, 272f, 303f)
            zonePx("Entrance / Exit (north)", ZoneKind.PLAIN, 327f, 41f, 345f, 51f)
            stairs()

            room("floor", "Ground floor - events & gatherings", 0f, 0f, 1f, 1f, computers = 2)
            val k = "floor"

            safety(k, alarms = SMOKE_DETECTORS_PER_FLOOR, sprinklers = SPRINKLERS_PER_FLOOR, lights = 16)
            put(AssetType.EXIT, k, 0.80f, 0.02f, "Exit (north entrance / exit) · Ground floor")
            put(AssetType.EXIT, k, 0.50f, 0.98f, "Main entrance / exit (south) · Ground floor")

            // sound system, stage and projection for events and campus gatherings
            spread(AssetType.MICROPHONE, k, 10, "Wireless microphone")
            spread(AssetType.MIC_STAND, k, 5, "Microphone stand")
            spread(AssetType.SPEAKER, k, 14, "PA speaker")
            spread(AssetType.PROJECTOR, k, 2, "Projector")
            spread(AssetType.SPECIAL_EQUIP, k, 2, "Projector screen")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Audio mixer & amplifier")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Stage lighting set")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Podium / lectern")
            spread(AssetType.TABLE_SET, k, 1, "Round tables with chairs (about 30 sets)")
            spread(AssetType.SEAT, k, 1, "Stackable chairs (about 300 pcs)")

            // reception / guest check-in
            spread(AssetType.COMPUTER, k, 2, "Reception computer")
            spread(AssetType.TABLE, k, 1, "Reception counter")
            spread(AssetType.SEAT, k, 1, "Waiting seats (about 12)")
            spread(AssetType.ID_SCANNER, k, 1, "Guest / visitor ID scanner")

            // catering pantry and storage of spare event gear
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Refrigerator (pantry)")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Gas range / stove (pantry)")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Water dispenser")
            spread(AssetType.TABLE, k, 2, "Food prep table")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Spare sound equipment (speakers, mics, cables)")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Spare tables & chairs (stock)")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Extension cords & cable reels")

            spread(AssetType.AIRCON, k, 12, "Air conditioner")
            spread(AssetType.FAN, k, 12, "Electric fan")
            spread(AssetType.WIFI, k, 10, "Wi-Fi access point")
            spread(AssetType.CCTV, k, 8, "CCTV camera")
        }.build()
    }

    // ---------------------------------------------------------------- 2ND: meetings
    private val second: FloorData by lazy {
        Layout(Floor.SECOND, "B20-2", secondBox).apply {
            stairs()

            room("floor", "2nd floor - meetings", 0f, 0f, 1f, 1f, computers = 2)
            val k = "floor"

            safety(k, alarms = SMOKE_DETECTORS_PER_FLOOR, sprinklers = SPRINKLERS_PER_FLOOR, lights = 16)
            put(AssetType.EXIT, k, 0.885f, 0.06f, "Fire exit (north-east stairs) · 2nd floor")
            put(AssetType.EXIT, k, 0.50f, 0.55f, "Fire exit (center stairs) · 2nd floor")

            spread(AssetType.MICROPHONE, k, 12, "Conference microphone")
            spread(AssetType.MIC_STAND, k, 4, "Microphone stand")
            spread(AssetType.SPEAKER, k, 12, "Speaker")
            spread(AssetType.PROJECTOR, k, 4, "Projector")
            spread(AssetType.SPECIAL_EQUIP, k, 4, "Projector screen")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Audio mixer & amplifier")
            spread(AssetType.SPECIAL_EQUIP, k, 2, "Video conferencing TV & camera")
            spread(AssetType.WHITEBOARD, k, 5, "Whiteboard")
            spread(AssetType.COMPUTER, k, 2, "Presentation computer")
            spread(AssetType.TABLE_SET, k, 1, "Meeting & conference tables with chairs (about 200 seats)")
            spread(AssetType.SEAT, k, 1, "Stackable chairs (about 150 pcs)")

            spread(AssetType.AIRCON, k, 12, "Air conditioner")
            spread(AssetType.FAN, k, 8, "Electric fan")
            spread(AssetType.WIFI, k, 10, "Wi-Fi access point")
            spread(AssetType.CCTV, k, 8, "CCTV camera")
        }.build()
    }

    // ---------------------------------------------------------------- 3RD: accommodation
    private val third: FloorData by lazy {
        Layout(Floor.THIRD, "B20-3", thirdBox).apply {
            stairs()

            room("floor", "3rd floor - accommodation", 0f, 0f, 1f, 1f)
            val k = "floor"

            safety(k, alarms = SMOKE_DETECTORS_PER_FLOOR, sprinklers = SPRINKLERS_PER_FLOOR, lights = 16)
            put(AssetType.EXIT, k, 0.02f, 0.50f, "Fire exit (west end) · 3rd floor")
            put(AssetType.EXIT, k, 0.98f, 0.50f, "Fire exit (east end) · 3rd floor")
            put(AssetType.EXIT, k, 0.885f, 0.06f, "Fire exit (north-east stairs) · 3rd floor")
            put(AssetType.EXIT, k, 0.50f, 0.55f, "Fire exit (center stairs) · 3rd floor")

            // 16 guest rooms, each with its own aircon and fan
            guestRooms(k, firstNo = 301, count = 16)

            // common lounge & dining, also usable for small gatherings
            spread(AssetType.TABLE_SET, k, 1, "Dining tables with chairs (about 12 sets)")
            spread(AssetType.SEAT, k, 1, "Lounge sofas & chairs (about 20)")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Television (lounge)")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Water dispenser")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Refrigerator (dining)")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Microwave oven")
            spread(AssetType.SPEAKER, k, 4, "Speaker (lounge)")
            spread(AssetType.SPEAKER, k, 4, "Speaker (corridor announcements)")
            spread(AssetType.MICROPHONE, k, 3, "Wireless microphone (lounge)")
            spread(AssetType.MIC_STAND, k, 1, "Microphone stand (lounge)")
            spread(AssetType.PROJECTOR, k, 1, "Projector (lounge)")
            spread(AssetType.AIRCON, k, 4, "Air conditioner (lounge)")
            spread(AssetType.FAN, k, 3, "Electric fan (lounge)")

            // laundry & linen
            spread(AssetType.SPECIAL_EQUIP, k, 2, "Washing machine")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Clothes dryer")
            spread(AssetType.SPECIAL_EQUIP, k, 1, "Linen storage racks")

            spread(AssetType.WIFI, k, 10, "Wi-Fi access point")
            spread(AssetType.CCTV, k, 6, "CCTV camera (corridor)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 20) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            Floor.THIRD -> third
            else -> null
        }
    }
}