package ph.edu.batstateu.arasof.arasofcampusassetmapping

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.AssetTagGenerator
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData
import ph.edu.batstateu.arasof.arasofcampusassetmapping.data.repository.InMemoryIncidentRepository
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.service.IncidentService
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.user.MaintenanceStaff
import ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.user.Student
import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.BuildingStatus

class CampusAssetTest {

    @Before
    fun setup() {
        AssetTagGenerator.populateTags()
    }

    @Test
    fun testUniqueAssetIdsAndTags() {
        val allAssetIds = mutableSetOf<String>()
        val allAssetTags = mutableSetOf<String>()

        for (building in CampusData.buildings) {
            if (!building.hasPlan) continue
            for (floor in building.floors) {
                val floorData = CampusData.floorData(building, floor)
                for (asset in floorData.assets) {
                    assertTrue("Duplicate asset ID: ${asset.id}", allAssetIds.add(asset.id))
                    if (asset.tag.isNotBlank()) {
                        assertTrue("Duplicate asset tag: ${asset.tag}", allAssetTags.add(asset.tag))
                    }
                }
            }
        }
    }

    @Test
    fun testRoomIdExistsOnSameFloor() {
        for (building in CampusData.buildings) {
            if (!building.hasPlan) continue
            for (floor in building.floors) {
                val floorData = CampusData.floorData(building, floor)
                val roomIds = floorData.rooms.map { it.id }.toSet()
                for (asset in floorData.assets) {
                    if (asset.roomId != null) {
                        assertTrue("Asset ${asset.id} references non-existent roomId ${asset.roomId} in building ${building.id}", roomIds.contains(asset.roomId))
                    }
                }
            }
        }
    }

    @Test
    fun testUserRolesAndPermissions() {
        val student = Student("Alice")
        val admin = MaintenanceStaff("Bob")

        assertFalse(student.canResolveIncident())
        assertTrue(admin.canResolveIncident())
    }

    @Test
    fun testIncidentServiceBusinessRules() {
        val repo = InMemoryIncidentRepository()
        val service = IncidentService(repo)
        val student = Student("Alice")
        val admin = MaintenanceStaff("Bob")

        val b = CampusData.buildings.first { it.hasPlan }
        val floorData = CampusData.floorData(b, b.floors.first())
        val asset = floorData.assets.first()
        val incidents = mutableListOf<ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Incident>()

        // Report issue
        service.reportIssue(incidents, asset, "Broken AC", student)
        assertEquals(1, incidents.size)
        val incident = incidents.first()

        // Report same issue again (joining)
        service.reportIssue(incidents, asset, "Broken AC too", Student("Charlie"))
        assertEquals(1, incidents.size) // still 1 incident
        assertTrue(incidents.first().alsoReportedBy.contains("Charlie"))

        // Student tries to resolve -> error / ignored
        val err = service.resolve(incidents, incident, "Fixed", student)
        assertNotNull(err)

        // Maintenance resolves
        val resErr = service.resolve(incidents, incident, "Fixed by IT", admin)
        assertNull(resErr)
        assertEquals(ph.edu.batstateu.arasof.arasofcampusassetmapping.model.IncidentStatus.RESOLVED, incidents.first().status)
    }

    @Test
    fun testBuildingPlanRegistry() {
        for (building in CampusData.buildings) {
            if (building.hasPlan && building.status == BuildingStatus.OPEN) {
                for (floor in building.floors) {
                    val floorData = CampusData.floorData(building, floor)
                    assertNotNull("Plan for building ${building.id} floor $floor should not be null", floorData)
                }
            }
        }
    }
}
