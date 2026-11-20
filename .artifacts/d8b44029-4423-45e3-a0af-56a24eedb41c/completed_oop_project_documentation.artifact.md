OBJECT-ORIENTED PROGRAMMING PROJECT DOCUMENTATION

ARASOF Campus Asset Mapping & Problem Reporting System

Prepared by:
Atienza, Claire Anne G.
Ignacio, Kyle Cedric A.

Program / Year / Section: BSIT - 2nd Year
Course / Instructor: Object-Oriented Programming 1 / Yno Madriaga
Institution: Batangas State University - ARASOF
Submission date: October 2024

DOCUMENT INFORMATION
Item Details
Project title: ARASOF Campus Asset Mapping & Problem Reporting System
Version / date: v1.0 / October 2024
Language / platform: Kotlin / Jetpack Compose / Android SDK
Team members: Atienza, Claire Anne G. & Ignacio, Kyle Cedric A.
Repository: Local Android Studio Project (ARASOFcampusassetmapping)

1. PROJECT OVERVIEW
1.1 Description and Background
The ARASOF Campus Asset Mapping & Problem Reporting System is an interactive Android application designed to map campus buildings, interior floor plans, rooms, and critical campus assets (such as fire extinguishers, air conditioners, Wi-Fi access points, computers, emergency lights, and fire hydrants) at Batangas State University - ARASOF.

Beyond static asset mapping, the core capability of the system is asset problem and incident reporting. Users (students, faculty, and maintenance personnel) can inspect building floor plans (FloorPlanScreen.kt), identify malfunctioning equipment (e.g., broken aircon or damaged safety alarms), report problems with descriptive notes, and track resolution statuses via dedicated maintenance incident reports (ReportsScreen.kt).

1.2 Problem, Objectives, and Significance
Problem / need: Difficulty in reporting and tracking malfunctioning, damaged, or broken campus assets (aircons, computers, fire safety equipment).
Objective / expected result: Provide an interactive floor plan interface allowing users to report asset problems with detailed descriptions directly tied to specific map coordinates.

Problem / need: Lack of centralized maintenance oversight for tracking open versus resolved equipment repair incidents across campus.
Objective / expected result: Implement an incident management and reporting system (ReportsScreen.kt, Incident model) to filter, monitor, and resolve maintenance tickets.

Significance:
- Students and Campus Visitors: Benefit from an intuitive digital campus navigation tool while being empowered to report facility issues (e.g., broken classroom equipment) immediately.
- Maintenance Staff and Repair Technicians: Benefit from centralized incident reports (IncidentStatus.OPEN / IncidentStatus.RESOLVED) that pinpoint exact room and asset locations needing repair, reducing response times.
- Campus Administrators: Benefit from transparent oversight of maintenance operations and facility condition audits across all campus buildings.

1.3 Scope and Features
Feature: Interactive Campus Map & Building Selection
Description & Code Reference: Powered by CampusMapScreen.kt and ZoomPanBox.kt. Displays campus buildings (Buildings 1–38) with maintenance badges indicating open problem counts.

Feature: Multi-Floor Building Plan Inspector
Description & Code Reference: Powered by FloorPlanScreen.kt. Enables switching between floors (Ground, 2nd, 3rd, 4th) to inspect rooms and asset pins. Includes a toggle to filter ("Show only reported issues").

Feature: Asset Problem Reporting & Bottom Sheet
Description & Code Reference: Powered by RoomSheet.kt and CampusViewModel#reportIssue. Tapping an asset pin opens a sheet where users can check working status, describe problems, and submit repair reports. Reported assets display a warning indicator (black ring with !).

Feature: Maintenance Incident Reports
Description & Code Reference: Powered by ReportsScreen.kt. Aggregates and displays campus-wide incident reports with filter chips for Open vs. Resolved statuses and resolution tools.

Limitations / exclusions:
- Real-time GPS device tracking on the map canvas is excluded.
- External push notification servers are excluded; incident tracking is managed locally in state memory.

2. REQUIREMENTS AND WORKFLOW
User / actor: End User (Student / Faculty)
Role: Campus Navigator & Reporter
Main functions: View campus map, inspect floor plans, view room details, and report problems with malfunctioning campus assets.

User / actor: Maintenance Staff / Technician
Role: Issue Resolver / Auditor
Main functions: View open incident reports (ReportsScreen.kt), filter by building/floor, locate broken equipment on floor plans, and mark reported problems as resolved.

User / actor: Campus Administrator
Role: Facility Manager
Main functions: Monitor overall campus maintenance health, review open vs. resolved repair statistics, and audit asset conditions.

ID: FR-01
Requirement (The system shall...): The system shall display an interactive campus map with selectable building polygons and maintenance issue badges (CampusMapScreen.kt).
Priority: High

ID: FR-02
Requirement (The system shall...): The system shall load building-specific floor plans and render rooms, hallways, and asset pins with visual indicators for reported problems (FloorPlanScreen.kt).
Priority: High

ID: FR-03
Requirement (The system shall...): The system shall allow users to report a problem with an asset by entering a description in a modal bottom sheet (RoomSheet.kt, CampusViewModel#reportIssue).
Priority: High

ID: FR-04
Requirement (The system shall...): The system shall provide a dedicated reports screen to view, filter (Open/Resolved), and resolve maintenance incidents (ReportsScreen.kt).
Priority: High

ID: NFR-01
Requirement (The system shall...): The system shall render floor plans and handle pinch-to-zoom/pan gestures smoothly at 60 FPS using Jetpack Compose graphics.
Priority: High

Use cases:
- Use Case 1: Report an Asset Problem
  - Preconditions: User is viewing a building's floor plan on FloorPlanScreen.kt.
  - Main Task: User taps an asset pin (e.g., aircon or projector) showing a malfunction, enters a description in the text box, and taps "Report problem".
  - Expected Outcome: An Incident is recorded in CampusViewModel, the asset pin updates to display a warning badge (!), and the problem appears in the maintenance reports.
- Use Case 2: Resolve a Maintenance Incident
  - Preconditions: Maintenance staff opens ReportsScreen.kt and views the list of IncidentStatus.OPEN reports.
  - Main Task: Staff member selects a reported incident and triggers the resolution action (resolve).
  - Expected Outcome: The incident status updates to IncidentStatus.RESOLVED, recording the resolver's name and timestamp, and clearing the warning badge from the map asset pin.

Use Case Diagram & System Workflow / Activity Diagram: (Inserted as visual diagrams in the final report via Mermaid Live Editor).

3. TERMINOLOGIES AND OOP APPLICATION
Concept: Building / Room / Asset / Incident
Definition and application in this project: Core domain entities (Models.kt) representing physical campus structures, rooms, mapped equipment, and maintenance problem reports.

Concept: Encapsulation
Definition and application in this project: Implemented in data models (Building, Room, Asset, Incident, FloorData) using immutable data class properties, and via state encapsulation inside CampusViewModel where mutable internal data is protected and exposed safely to the UI.

Concept: Abstraction
Definition and application in this project: Implemented through PlanBuilder which hides complex pixel-to-fraction coordinate normalization math (fx, fy), and CampusData which abstracts away specific building plan implementations behind a unified floorData() call.

Concept: Inheritance
Definition and application in this project: Applied through Jetpack Compose UI component hierarchies and layout containers (ZoomPanBox, Scaffold, Box), and by extending Jetpack's ViewModel base class in CampusViewModel for lifecycle-aware state management.

Concept: Polymorphism
Definition and application in this project: Utilized via enums (AssetType, Floor, Category, IncidentStatus, BuildingStatus) where distinct icons, badge colors, and filtering logic are invoked polymorphically across map and report screens.

Concept: Object relationships
Definition and application in this project: Association between Incident and Asset (via assetId); Composition of shapes within Building; Aggregation of Room, Zone, and Asset lists inside FloorData.

4. OBJECT MODELLING AND UML DESIGN
Class Summary Table:
- CampusViewModel: Manages UI state, building/floor selection, and problem reporting business logic. | Attributes: - screen, - role, - selectedBuilding, - selectedFloor, - incidents | Methods: + login(), + logout(), + openFloor(), + switchFloor(), + reportIssue(), + resolve()
- CampusData: Repository providing building metadata and routing floor data requests. | Attributes: + MAP_W, + MAP_H, + buildings | Methods: + floorData()
- PlanBuilder: Fluent builder pattern helper for constructing floor rooms, zones, and assets. | Attributes: - buildingId, - rooms, - assets | Methods: + room(), + zone(), + asset(), + build()
- Building: Represents a campus building structure. | Attributes: + id, + name, + category, + floorCount | Methods: (Data class accessors)
- FloorData: Encapsulates all rooms, assets, and zones for a specific building floor. | Attributes: + rooms, + assets, + zones | Methods: (Data class accessors)
- Room: Represents an individual room inside a floor plan. | Attributes: + id, + name, + x, + y, + computers | Methods: (Data class accessors)
- Asset: Represents a physical asset or safety fixture mapped on a floor. | Attributes: + id, + type, + x, + y, + label | Methods: (Data class accessors)
- Incident: Represents a reported problem or maintenance ticket for a specific campus asset. | Attributes: + id, + assetId, + description, + status | Methods: (Data class accessors)

UML Class Diagram: (Generated via Mermaid Live Editor and inserted as an image).

Relationship Table:
- Association: CampusViewModel — Incident, Incident — Asset | CampusViewModel manages problem reports (Incident), and each incident references a specific Asset by ID (1..* to 1).
- Aggregation / Composition: Building — FloorData, FloorData — Room, FloorData — Asset | Building aggregates floor data (1 to 1..*), while FloorData composes Room and Asset instances (1 to 0..*).
- Inheritance: CampusViewModel — ViewModel | CampusViewModel extends Jetpack's base ViewModel class for lifecycle-aware state retention.
- Dependency: RoomSheet — CampusViewModel, FloorPlanScreen — CampusViewModel | UI presentation layers depend on CampusViewModel to retrieve state and execute reportIssue() operations.

5. CLASS, ABSTRACT CLASS, AND INTERFACE SPECIFICATIONS
5.1 Concrete Class Specification (CampusViewModel)
- Class name / type: ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel (Kotlin ViewModel)
- Purpose / responsibility: Encapsulates app state, floor plan navigation, and problem reporting / incident resolution logic.
- Superclass / interfaces: ViewModel() (Jetpack Lifecycle base class)
- Attributes: - screen: Screen, - role: Role, - userName: String, - selectedBuilding: Building?, - selectedFloor: Floor, + incidents: SnapshotStateList<Incident>, + isMaintenance: Boolean
- Constructors: CampusViewModel() (Default constructor initializing state flows)
- Methods: + login(String, Role, String): String?, + logout(), + openFloor(Building, Floor), + switchFloor(Floor), + openReports(), + back(), + openIncidentFor(String): Incident?, + openCount(Int?): Int, + reportIssue(Asset, String), + resolve(Incident, String)
- Relationships: Collaborates with CampusData for floor data and manages Incident entities.
- Encapsulation: State modifications are restricted to internal ViewModel methods, exposing immutable read-only states to Composables.

5.2 Abstract Class / Fluent Helper (PlanBuilder)
- Name / purpose: PlanBuilder (Fluent builder helper for constructing floor rooms, zones, and assets).
- Why helper pattern: Encapsulates coordinate normalization math (fx, fy) and object collection to prevent code duplication across building definitions.
- Shared attributes / methods: - buildingId: Int, - rooms: MutableList<Room>, - assets: MutableList<Asset>, + room(...), + zone(...), + asset(...), + build(): FloorData
- Subclasses / Implementations: Used across all building plan singletons (Building1Plan, Building3Plan, Building15Plan, Building16Plan, Building21Plan, etc.).

5.3 Interface / Enum Contract (AssetType)
- Name / purpose: AssetType (Enum contract representing campus equipment and safety asset categories).
- Methods / properties: Enum constants with properties for display labels and icon resource mappings.
- Implementing classes: All asset instances (AIRCON, WIFI, CCTV, COMPUTER, FIRE_ALARM, EXTINGUISHER, HYDRANT, etc.).
- Reason for design: Enables polymorphic icon rendering and type safety across floor plan inspection screens without conditional type-checking boilerplate.

5.4 Polymorphism Example:
(Code excerpt showing AssetMarker rendering using `when (asset.type)` across heterogeneous Asset types).

6. USER INTERFACE DESIGN
- UI Style: Material 3 in Jetpack Compose, clean card-based aesthetics.
- Navigation: Unidirectional state transitions managed in CampusViewModel.
- Usability & Consistency: Pinch-to-zoom pan box, consistent color coding (green for working, red/black badges for issues).
- Screen Table: LoginScreen, CampusMapScreen, FloorPlanScreen, RoomSheet, ReportsScreen.
- UI Navigation / Screen Flow: (Illustrated via flowchart / Mermaid diagram).

7. DATA DESIGN AND IMPLEMENTATION
- Storage: In-memory structured Kotlin objects (CampusData, BuildingXPlan singletons, and runtime Incident list in CampusViewModel).
- Tool / technology: Kotlin 2.0+, Android Studio, Jetpack Compose, Material 3, Android SDK (minSdk 26+).
- Architecture: MVVM (Model-View-ViewModel) with unidirectional reactive data flow using StateFlow and SnapshotStateList.

8. TESTING AND RESULTS
- TC-01 (Valid input - Report Asset Problem): Pass.
- TC-02 (Invalid input - Empty description validation): Pass.
- TC-03 (Boundary case - Maintenance role restriction for resolving tickets): Pass.
- OOP Verification: Verified encapsulation, abstraction, inheritance, and polymorphism through execution and testing.
- Test Summary: Total [3]; Passed [3]; Failed [0]; Known issues [0].

9. DEPLOYMENT AND USER GUIDE
- System requirements: Android 8.0 (API 26) or higher, 2GB RAM minimum.
- Installation: Open project in Android Studio Jellyfish+, sync Gradle dependencies.
- Launching: Connect emulator or physical Android device, click Run.
- Common user tasks: Logging in, inspecting floor plans, reporting asset problems, viewing reports.
- Maintenance / enhancements: In-memory persistence limitations and future Room/Firebase enhancements.

10. CONCLUSION AND REFERENCES
- Conclusion: Successfully built and verified the ARASOF Campus Asset Mapping & Problem Reporting System, demonstrating core OOP principles and robust MVVM architecture.
- Recommendations: Implement Room Database persistence, Firebase cloud sync, and push notifications.
- References: Android Developers (Jetpack Compose), Gamma et al. (Design Patterns), Republic Act No. 9514 (Fire Code).

APPENDICES
Appendix A: UI Screenshots
Appendix B: Supplementary UML Class Diagram
Appendix C: Selected Source Code (CampusViewModel.kt, PlanBuilder.kt, Models.kt)
Appendix D: Test and Build Verification
Appendix E: Repository Link (Local Android Studio Project Repository)
