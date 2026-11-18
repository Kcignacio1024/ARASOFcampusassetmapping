package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.*

/**
 * Real floor plans of building 16 (Lemery Building): ground floor and 2nd floor.
 * Numbers are PIXELS of the plan pictures (1920 x 1080).
 *
 * This building is full of laboratories, so each lab only gets the important equipment that is unique to
 * it (for example the autoclave and biosafety cabinet in the Micro Lab, the fume hood and eyewash in the
 * Physico-Chemical Laboratory), plus the basics: air conditioners, Wi-Fi, CCTV and fire safety.
 * The LB rooms are plain LECTURE rooms: Wi-Fi, projector and a fire detector. Only LB104 has air
 * conditioners; LB101-LB103 and LB201-LB203 do not. Their fire extinguishers are OUTSIDE, in the
 * walkway / hallway. The small room beside the Nutrition Lab is part of the Nutrition Lab.
 * Everything is an estimate: edit freely.
 *
 * FIRE HYDRANTS (ground floor, estimated, confirm with the BFP / campus engineer): three on open ground,
 * in the courtyard east of LB101-LB103 and south of the Nursing Skills Lab and the Amphitheater. This
 * keeps every part of the building within two hose lengths (2 x 20 m) of a hydrant (Fire Code of the
 * Philippines, RA 9514 IRR).
 */
object Building16Plan {
    private val ground: FloorData by lazy {
        PlanBuilder(16, Floor.GROUND, "B16-1", 30.0f, 10.0f, 1500.0f, 1090.0f).apply {
            room("cr", "CR Male / Female", 350.0f, 38.0f, 552.0f, 138.0f, computers = 0)
            room("nutrition", "Nutrition Lab", 78.0f, 98.0f, 302.0f, 466.0f, computers = 0, outline = listOf(105.0f to 98.0f, 302.0f to 98.0f, 302.0f to 375.0f, 143.0f to 375.0f, 143.0f to 466.0f, 78.0f to 462.0f, 78.0f to 372.0f, 105.0f to 372.0f))
            room("physico", "Physico-Chemical Laboratory", 152.0f, 470.0f, 300.0f, 685.0f, computers = 0)
            room("sensory", "Sensory Laboratory", 152.0f, 690.0f, 300.0f, 915.0f, computers = 0)
            room("micro", "Micro Lab", 350.0f, 182.0f, 520.0f, 375.0f, computers = 0)
            room("anaphy", "Anaphy Lab", 350.0f, 378.0f, 520.0f, 568.0f, computers = 0)
            room("faculty", "Faculty Room / Deans Office", 350.0f, 572.0f, 520.0f, 765.0f, computers = 6)
            room("lb104", "LB104", 350.0f, 770.0f, 522.0f, 940.0f, computers = 0)
            room("levis", "Levis Room / Nutrition Clinic", 525.0f, 775.0f, 650.0f, 940.0f, computers = 2)
            room("lb101", "LB101", 640.0f, 60.0f, 806.0f, 225.0f, computers = 0)
            room("lb102", "LB102", 640.0f, 235.0f, 806.0f, 395.0f, computers = 0)
            room("lb103", "LB103", 640.0f, 405.0f, 806.0f, 568.0f, computers = 0)
            room("nursing", "Nursing Skills Lab", 652.0f, 758.0f, 1195.0f, 950.0f, computers = 0)
            room("amphi", "Amphitheater", 1200.0f, 758.0f, 1440.0f, 950.0f, computers = 0)
            zone("Hallway", ZoneKind.CORRIDOR, 305.0f, 85.0f, 348.0f, 915.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 348.0f, 140.0f, 520.0f, 180.0f)
            zone("Walkway", ZoneKind.CORRIDOR, 525.0f, 40.0f, 638.0f, 570.0f)
            zone("Hallway", ZoneKind.CORRIDOR, 305.0f, 945.0f, 1440.0f, 1005.0f)
            zone("Stairs up to 2nd floor", ZoneKind.STAIRS, 188.0f, 378.0f, 300.0f, 468.0f)
            asset(AssetType.AIRCON, 125.0f, 150.0f, "nutrition", "")
            asset(AssetType.SPECIAL_EQUIP, 205.0f, 270.0f, "nutrition", "Cooking stations (stoves / ovens)")
            asset(AssetType.SPECIAL_EQUIP, 205.0f, 330.0f, "nutrition", "Refrigerator / food storage (estimated)")
            asset(AssetType.SPECIAL_EQUIP, 205.0f, 125.0f, "nutrition", "Exhaust hood over cooking area (estimated)")
            asset(AssetType.FIRE_ALARM, 270.0f, 200.0f, "nutrition", "")
            asset(AssetType.EXTINGUISHER, 280.0f, 350.0f, "nutrition", "Fire extinguisher (near cooking area)")
            asset(AssetType.AIRCON, 375.0f, 205.0f, "micro", "")
            asset(AssetType.SPECIAL_EQUIP, 430.0f, 235.0f, "micro", "Autoclave / sterilizer")
            asset(AssetType.SPECIAL_EQUIP, 480.0f, 300.0f, "micro", "Biosafety cabinet")
            asset(AssetType.SPECIAL_EQUIP, 395.0f, 340.0f, "micro", "Incubator")
            asset(AssetType.SPECIAL_EQUIP, 440.0f, 330.0f, "micro", "Microscopes (estimated 20)")
            asset(AssetType.FIRE_ALARM, 470.0f, 215.0f, "micro", "")
            asset(AssetType.EXTINGUISHER, 500.0f, 360.0f, "micro", "")
            asset(AssetType.AIRCON, 375.0f, 400.0f, "anaphy", "")
            asset(AssetType.SPECIAL_EQUIP, 430.0f, 470.0f, "anaphy", "Anatomical models & skeleton")
            asset(AssetType.SPECIAL_EQUIP, 460.0f, 530.0f, "anaphy", "Examination / demonstration beds")
            asset(AssetType.FIRE_ALARM, 470.0f, 400.0f, "anaphy", "")
            asset(AssetType.EXTINGUISHER, 500.0f, 550.0f, "anaphy", "")
            asset(AssetType.AIRCON, 375.0f, 595.0f, "faculty", "")
            asset(AssetType.WIFI, 440.0f, 640.0f, "faculty", "")
            asset(AssetType.CCTV, 500.0f, 590.0f, "faculty", "")
            asset(AssetType.COMPUTER, 440.0f, 700.0f, "faculty", "Computers (about 6) · Faculty Room / Deans Office")
            asset(AssetType.FIRE_ALARM, 470.0f, 740.0f, "faculty", "")
            asset(AssetType.EXTINGUISHER, 500.0f, 745.0f, "faculty", "")
            asset(AssetType.AIRCON, 590.0f, 800.0f, "levis", "")
            asset(AssetType.WIFI, 625.0f, 830.0f, "levis", "")
            asset(AssetType.COMPUTER, 590.0f, 870.0f, "levis", "Computers (about 2) · Levis Room / Nutrition Clinic")
            asset(AssetType.SPECIAL_EQUIP, 600.0f, 905.0f, "levis", "Body composition analyzer & weighing scale (estimated)")
            asset(AssetType.FIRE_ALARM, 560.0f, 925.0f, "levis", "")
            asset(AssetType.SPECIAL_EQUIP, 200.0f, 520.0f, "physico", "Fume hood")
            asset(AssetType.SPECIAL_EQUIP, 210.0f, 580.0f, "physico", "Analytical balance & spectrophotometer")
            asset(AssetType.SPECIAL_EQUIP, 230.0f, 650.0f, "physico", "Chemical storage cabinet")
            asset(AssetType.SPECIAL_EQUIP, 270.0f, 500.0f, "physico", "Emergency eyewash & safety shower")
            asset(AssetType.AIRCON, 175.0f, 495.0f, "physico", "")
            asset(AssetType.FIRE_ALARM, 250.0f, 620.0f, "physico", "")
            asset(AssetType.EXTINGUISHER, 270.0f, 665.0f, "physico", "")
            asset(AssetType.SPECIAL_EQUIP, 225.0f, 740.0f, "sensory", "Sensory evaluation (taste test) booths")
            asset(AssetType.SPECIAL_EQUIP, 230.0f, 850.0f, "sensory", "Sample preparation counter & refrigerator (estimated)")
            asset(AssetType.AIRCON, 175.0f, 715.0f, "sensory", "")
            asset(AssetType.FIRE_ALARM, 270.0f, 830.0f, "sensory", "")
            asset(AssetType.EXTINGUISHER, 275.0f, 890.0f, "sensory", "")
            asset(AssetType.SPECIAL_EQUIP, 760.0f, 800.0f, "nursing", "Hospital beds & patient mannequins (about 6 skill stations)")
            asset(AssetType.SPECIAL_EQUIP, 930.0f, 800.0f, "nursing", "Hospital beds & patient mannequins (middle stations)")
            asset(AssetType.SPECIAL_EQUIP, 900.0f, 915.0f, "nursing", "Medical supplies & equipment carts (estimated)")
            asset(AssetType.AIRCON, 690.0f, 775.0f, "nursing", "Air conditioner (west)")
            asset(AssetType.AIRCON, 1000.0f, 775.0f, "nursing", "Air conditioner (middle)")
            asset(AssetType.AIRCON, 1160.0f, 775.0f, "nursing", "Air conditioner (east)")
            asset(AssetType.WIFI, 930.0f, 860.0f, "nursing", "")
            asset(AssetType.CCTV, 1175.0f, 860.0f, "nursing", "")
            asset(AssetType.FIRE_ALARM, 800.0f, 860.0f, "nursing", "")
            asset(AssetType.EXTINGUISHER, 690.0f, 930.0f, "nursing", "")
            asset(AssetType.PROJECTOR, 1320.0f, 790.0f, "amphi", "Projector / presentation screen")
            asset(AssetType.SPECIAL_EQUIP, 1320.0f, 900.0f, "amphi", "Sound system (estimated)")
            asset(AssetType.AIRCON, 1230.0f, 790.0f, "amphi", "")
            asset(AssetType.AIRCON, 1410.0f, 790.0f, "amphi", "")
            asset(AssetType.WIFI, 1320.0f, 850.0f, "amphi", "")
            asset(AssetType.CCTV, 1420.0f, 930.0f, "amphi", "")
            asset(AssetType.FIRE_ALARM, 1230.0f, 930.0f, "amphi", "")
            asset(AssetType.EXTINGUISHER, 1415.0f, 865.0f, "amphi", "")
            asset(AssetType.CCTV, 330.0f, 300.0f, null, "CCTV camera (hallway, west)")
            asset(AssetType.CCTV, 330.0f, 700.0f, null, "CCTV camera (hallway, west)")
            asset(AssetType.FIRE_LIGHT, 330.0f, 520.0f, null, "Emergency light (hallway, west)")
            asset(AssetType.EXTINGUISHER, 335.0f, 420.0f, null, "Fire extinguisher (hallway, west)")
            asset(AssetType.EXTINGUISHER, 335.0f, 820.0f, null, "Fire extinguisher (hallway, west)")
            asset(AssetType.FIRE_LIGHT, 430.0f, 160.0f, null, "Emergency light (hallway, north)")
            asset(AssetType.CCTV, 580.0f, 200.0f, null, "CCTV camera (walkway)")
            asset(AssetType.FIRE_LIGHT, 580.0f, 400.0f, null, "Emergency light (walkway)")
            asset(AssetType.CCTV, 450.0f, 980.0f, null, "CCTV camera (south hallway)")
            asset(AssetType.CCTV, 1000.0f, 985.0f, null, "CCTV camera (south hallway)")
            asset(AssetType.FIRE_LIGHT, 650.0f, 980.0f, null, "Emergency light (south hallway)")
            asset(AssetType.FIRE_LIGHT, 1200.0f, 985.0f, null, "Emergency light (south hallway)")
            asset(AssetType.EXTINGUISHER, 880.0f, 985.0f, null, "Fire extinguisher (south hallway)")
            asset(AssetType.FIRE_LIGHT, 244.0f, 425.0f, null, "Emergency light (stairs)")
            asset(AssetType.EXIT, 735.0f, 950.0f, null, "Nursing Skills Lab exit")
            asset(AssetType.EXIT, 1110.0f, 950.0f, null, "Nursing Skills Lab entrance / exit")
            asset(AssetType.HYDRANT, 950.0f, 400.0f, null, "Fire hydrant (courtyard east of LB101-LB103 - estimated)")
            asset(AssetType.HYDRANT, 700.0f, 1050.0f, null, "Fire hydrant (outside, south of the Nursing Skills Lab - estimated)")
            asset(AssetType.HYDRANT, 1350.0f, 1050.0f, null, "Fire hydrant (outside, south of the Amphitheater - estimated)")
            asset(AssetType.WIFI, 723.0f, 85.0f, "lb101", "")
            asset(AssetType.PROJECTOR, 723.0f, 120.0f, "lb101", "")
            asset(AssetType.FIRE_ALARM, 698.0f, 200.0f, "lb101", "Fire detector")
            asset(AssetType.WIFI, 723.0f, 260.0f, "lb102", "")
            asset(AssetType.PROJECTOR, 723.0f, 295.0f, "lb102", "")
            asset(AssetType.FIRE_ALARM, 698.0f, 370.0f, "lb102", "Fire detector")
            asset(AssetType.WIFI, 723.0f, 430.0f, "lb103", "")
            asset(AssetType.PROJECTOR, 723.0f, 465.0f, "lb103", "")
            asset(AssetType.FIRE_ALARM, 698.0f, 543.0f, "lb103", "Fire detector")
            asset(AssetType.AIRCON, 375.0f, 788.0f, "lb104", "Air conditioner (north)")
            asset(AssetType.AIRCON, 497.0f, 922.0f, "lb104", "Air conditioner (south)")
            asset(AssetType.WIFI, 436.0f, 795.0f, "lb104", "")
            asset(AssetType.PROJECTOR, 436.0f, 830.0f, "lb104", "")
            asset(AssetType.FIRE_ALARM, 411.0f, 915.0f, "lb104", "Fire detector")
            asset(AssetType.EXTINGUISHER, 600.0f, 150.0f, null, "Fire extinguisher (walkway, outside LB101)")
            asset(AssetType.EXTINGUISHER, 600.0f, 320.0f, null, "Fire extinguisher (walkway, outside LB102)")
            asset(AssetType.EXTINGUISHER, 600.0f, 500.0f, null, "Fire extinguisher (walkway, outside LB103)")
            asset(AssetType.EXTINGUISHER, 440.0f, 955.0f, null, "Fire extinguisher (south hallway, outside LB104)")
        }.build()
    }

    private val second: FloorData by lazy {
        PlanBuilder(16, Floor.SECOND, "B16-2", 165.0f, 250.0f, 350.0f, 430.0f).apply {
            room("lb201", "LB201", 292.0f, 275.0f, 482.0f, 370.0f, computers = 0)
            room("lb202", "LB202", 355.0f, 375.0f, 482.0f, 565.0f, computers = 0)
            room("lb203", "LB203", 297.0f, 567.0f, 482.0f, 652.0f, computers = 0)
            zone("Hallway", ZoneKind.CORRIDOR, 297.0f, 375.0f, 352.0f, 565.0f)
            zone("Stairs", ZoneKind.STAIRS, 190.0f, 376.0f, 292.0f, 478.0f)
            asset(AssetType.CCTV, 325.0f, 430.0f, null, "CCTV camera (hallway)")
            asset(AssetType.FIRE_LIGHT, 325.0f, 520.0f, null, "Emergency light (hallway)")
            asset(AssetType.FIRE_LIGHT, 245.0f, 445.0f, null, "Emergency light (stairs)")
            asset(AssetType.EXIT, 232.0f, 425.0f, null, "Exit (stairs down to ground floor)")
            asset(AssetType.WIFI, 387.0f, 300.0f, "lb201", "")
            asset(AssetType.PROJECTOR, 387.0f, 335.0f, "lb201", "")
            asset(AssetType.FIRE_ALARM, 362.0f, 345.0f, "lb201", "Fire detector")
            asset(AssetType.WIFI, 418.5f, 400.0f, "lb202", "")
            asset(AssetType.PROJECTOR, 418.5f, 435.0f, "lb202", "")
            asset(AssetType.FIRE_ALARM, 393.5f, 540.0f, "lb202", "Fire detector")
            asset(AssetType.WIFI, 389.5f, 592.0f, "lb203", "")
            asset(AssetType.PROJECTOR, 389.5f, 627.0f, "lb203", "")
            asset(AssetType.FIRE_ALARM, 364.5f, 627.0f, "lb203", "Fire detector")
            asset(AssetType.EXTINGUISHER, 325.0f, 405.0f, null, "Fire extinguisher (hallway, outside LB201 / LB202)")
            asset(AssetType.EXTINGUISHER, 325.0f, 535.0f, null, "Fire extinguisher (hallway, outside LB202 / LB203)")
        }.build()
    }

    fun forFloor(buildingId: Int, floor: Floor): FloorData? {
        if (buildingId != 16) return null
        return when (floor) {
            Floor.GROUND -> ground
            Floor.SECOND -> second
            Floor.THIRD -> null
            Floor.FOURTH -> null
        }
    }
}