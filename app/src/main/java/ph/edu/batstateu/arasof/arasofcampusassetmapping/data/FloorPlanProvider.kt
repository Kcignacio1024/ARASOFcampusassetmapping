package ph.edu.batstateu.arasof.arasofcampusassetmapping.data

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Floor

/**
 * Interface for building floor plan providers.
 * Demonstrates Interface Segregation Principle (ISP) and Open-Closed Principle (OCP).
 */
interface FloorPlanProvider {
    fun forFloor(buildingId: Int, floor: Floor): FloorData?
}
