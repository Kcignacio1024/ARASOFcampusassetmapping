package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Floor
import java.util.Locale

object AssetTagGenerator {

    fun floorCode(floor: Floor): String = when (floor) {
        Floor.GROUND -> "G"
        Floor.SECOND -> "2"
        Floor.THIRD -> "3"
        Floor.FOURTH -> "4"
    }

    /**
     * Auto-generates stable tags for all assets across campus buildings.
     * Format: B{building}-{floor code}-{room key}-{type symbol}-{two-digit number}
     * Example: B23-G-HALL-ID-03
     */
    fun populateTags() {
        for (building in CampusData.buildings) {
            if (!building.hasPlan) continue
            for (floor in building.floors) {
                val floorData = CampusData.floorData(building, floor)
                
                // Track counts per room + type combination
                val counters = mutableMapOf<String, Int>()

                for (asset in floorData.assets) {
                    val fCode = floorCode(floor)
                    val rKey = if (!asset.roomId.isNullOrBlank()) asset.roomId.uppercase().replace(Regex("[^A-Z0-9]"), "") else "OUT"
                    val tSymbol = asset.type.symbol

                    val key = "${building.id}-$fCode-$rKey-$tSymbol"
                    val count = (counters[key] ?: 0) + 1
                    counters[key] = count

                    val formattedNum = String.format(Locale.US, "%02d", count)
                    asset.tag = "B${building.id}-$fCode-$rKey-$tSymbol-$formattedNum"
                }
            }
        }
    }
}
