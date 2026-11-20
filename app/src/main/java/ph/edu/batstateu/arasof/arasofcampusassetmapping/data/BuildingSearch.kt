package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Building

data class SearchResult(
    val building: Building,
    val score: Int, // higher is better
    val matchType: MatchType
)

enum class MatchType { EXACT, PREFIX, CONTAINS, ALIAS, FUZZY }

object BuildingSearch {

    // Easy-to-edit map of aliases to building IDs or keywords
    private val aliases = mapOf(
        "gym" to listOf(3, 11, 21), // Gyms
        "gymnasium" to listOf(3, 11, 21),
        "canteen" to listOf(4, 13),
        "cafeteria" to listOf(4, 13),
        "clinic" to listOf(1, 10),
        "infirmary" to listOf(1, 10),
        "library" to listOf(15, 16),
        "hostel" to listOf(19),
        "guard" to (32..38).toList(),
        "water tank" to (39..46).toList(),
        "lipa" to listOf(1),
        "lemery" to listOf(2)
    )

    fun search(query: String): Pair<List<SearchResult>, List<Building>> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return emptyList<SearchResult>() to emptyList()

        val results = mutableListOf<SearchResult>()

        for (building in CampusData.buildings) {
            val name = building.name.lowercase()
            val idStr = building.id.toString()
            val category = building.category.label.lowercase()

            var score = 0
            var matchType = MatchType.FUZZY

            // 1. Exact match on number or name
            if (idStr == q || name == q) {
                score = 100
                matchType = MatchType.EXACT
            }
            // 2. Prefix match
            else if (name.startsWith(q) || idStr.startsWith(q)) {
                score = 80
                matchType = MatchType.PREFIX
            }
            // 3. Contains match
            else if (name.contains(q) || category.contains(q)) {
                score = 60
                matchType = MatchType.CONTAINS
            }
            // 4. Alias match
            else {
                for ((alias, bIds) in aliases) {
                    if (alias.contains(q) || q.contains(alias)) {
                        if (bIds.contains(building.id)) {
                            score = 70
                            matchType = MatchType.ALIAS
                            break
                        }
                    }
                }
            }

            // 5. Fuzzy / edit distance / trigram similarity if no high score
            if (score == 0) {
                val dist = levenshteinDistance(q, name)
                val maxLen = maxOf(q.length, name.length)
                val similarity = if (maxLen > 0) (1.0 - dist.toDouble() / maxLen) else 0.0
                if (similarity > 0.4 || name.split(" ").any { levenshteinDistance(q, it) <= 2 }) {
                    score = (similarity * 40).toInt().coerceAtLeast(10)
                    matchType = MatchType.FUZZY
                }
            }

            if (score > 0) {
                results.add(SearchResult(building, score, matchType))
            }
        }

        val sortedResults = results.sortedByDescending { it.score }
        
        // If nothing or few matches, find suggestions ("Did you mean")
        val suggestions = if (sortedResults.isEmpty() || sortedResults.first().score < 50) {
            CampusData.buildings
                .sortedBy { levenshteinDistance(q, it.name.lowercase()) }
                .take(3)
        } else {
            emptyList()
        }

        return sortedResults to suggestions
    }

    private fun levenshteinDistance(lhs: CharSequence, rhs: CharSequence): Int {
        val dt = Array(lhs.length + 1) { IntArray(rhs.length + 1) }
        for (i in 0..lhs.length) dt[i][0] = i
        for (j in 0..rhs.length) dt[0][j] = j

        for (i in 1..lhs.length) {
            for (j in 1..rhs.length) {
                val cost = if (lhs[i - 1] == rhs[j - 1]) 0 else 1
                dt[i][j] = minOf(
                    dt[i - 1][j] + 1,
                    dt[i][j - 1] + 1,
                    dt[i - 1][j - 1] + cost
                )
            }
        }
        return dt[lhs.length][rhs.length]
    }
}
