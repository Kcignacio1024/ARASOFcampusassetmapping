# OBJECT-ORIENTED PROGRAMMING PROJECT DOCUMENTATION

**ARASOF Campus Asset Mapping System**
**Prepared by:** Student Developer
**Program / Year / Section:** BSIT - 2nd Year
**Course / Instructor:** Object-Oriented Programming 1 / Yno Madriaga
**Institution:** Batangas State University - ARASOF
**Submission date:** October 2024 (or Current Date)

---

## DOCUMENT INFORMATION
| Item | Details |
| :--- | :--- |
| Project title | ARASOF Campus Asset Mapping System |
| Version / date | v1.0 / October 2024 |
| Language / platform | Kotlin / Jetpack Compose / Android SDK |
| Team members | Student Developer |
| Repository | Local Android Studio Project (ARASOFcampusassetmapping) |

---

## 1. PROJECT OVERVIEW

### 1.1 Description and Background
The **ARASOF Campus Asset Mapping System** is an interactive Android application designed to map campus buildings, interior floor plans, rooms, and critical campus assets (such as fire extinguishers, air conditioners, Wi-Fi access points, computers, emergency lights, and fire hydrants) at Batangas State University - ARASOF.

The software solves the challenge of campus navigation and asset inventory management by providing an intuitive 2D zoomable/pannable map interface ([CampusMapScreen.kt](file:///C:/Users/KCignacio/AndroidStudioProjects/ARASOFcampusassetmapping/app/src/main/java/ph/edu/batstateu/arasof/arasofcampusassetmapping/ui/CampusMapScreen.kt)) paired with detailed floor plans ([FloorPlanScreen.kt](file:///C:/Users/KCignacio/AndroidStudioProjects/ARASOFcampusassetmapping/app/src/main/java/ph/edu/batstateu/arasof/arasofcampusassetmapping/ui/FloorPlanScreen.kt)). Intended users include students seeking building locations, faculty locating offices or laboratories, and campus administrators or safety officers tracking facility assets and fire code compliance (RA 9514).

### 1.2 Problem, Objectives, and Significance
| Problem / need | Objective / expected result |
| :--- | :--- |
| Difficulty in locating specific rooms, offices, and laboratories across multiple campus buildings. | Provide an interactive campus map and multi-floor building plans with searchable room directories. |
| Lack of centralized inventory and safety asset tracking (e.g., fire extinguishers, emergency lights, hydrants). | Simulate and categorize estimated campus assets per room and hallway coordinates based on architectural layout assumptions and safety guidelines. |

**Significance:** Campus administrators benefit from streamlined asset tracking and safety compliance audits, while students and visitors benefit from quick, offline-capable digital campus navigation.

### 1.3 Scope and Features
| Feature | Description |
| :--- | :--- |
| **Interactive Campus Map** | Zoomable and pannable overview of campus buildings (Buildings 1–38) with category-based color coding. |
| **Floor Plan Inspector** | Multi-floor views (Ground, 2nd, 3rd, 4th floors) displaying rooms, hallways, stairwells, and asset pins. |
| **Asset & Room Inspection** | Tapping rooms or asset pins opens detailed sheets ([RoomSheet.kt](file:///C:/Users/KCignacio/AndroidStudioProjects/ARASOFcampusassetmapping/app/src/main/java/ph/edu/batstateu/arasof/arasofcampusassetmapping/ui/RoomSheet.kt)) showing computer counts, air conditioners, fire alarms, and equipment. |
| **Campus Reports** | Summary reports ([ReportsScreen.kt](file:///C:/Users/KCignacio/AndroidStudioProjects/ARASOFcampusassetmapping/app/src/main/java/ph/edu/batstateu/arasof/arasofcampusassetmapping/ui/ReportsScreen.kt)) listing total assets, fire safety fixtures, and facility counts. |

**Limitations / exclusions:** Real-time GPS tracking and cloud database synchronization are excluded from the current scope; floor plans and asset locations are stored as structured static data builders in local memory.

---

## 2. REQUIREMENTS AND WORKFLOW

### User Actors and Functions
| User / actor | Role | Main functions |
| :--- | :--- | :--- |
| **End User (Student / Visitor)** | Navigator / Viewer | View campus map, select buildings, inspect floor plans, view room details and asset locations. |
| **Administrator / Safety Officer** | Auditor / Manager | Review asset distribution reports, check fire safety equipment placement, inspect room inventories. |

### System Requirements
| ID | Requirement (The system shall...) | Priority |
| :--- | :--- | :--- |
| **FR-01** | The system shall display an interactive campus map with selectable building polygons. | High |
| **FR-02** | The system shall load specific floor plans (Ground, 2nd, 3rd, 4th) when a building is selected. | High |
| **FR-03** | The system shall display asset detail cards when user taps rooms or map asset pins. | Medium |
| **NFR-01** | The system shall render zoomable floor plans smoothly at 60 FPS using Jetpack Compose graphics. | High |

**Use cases:**
1. *Select Building*: User opens app -> views campus map -> taps Building 3 (Taal Building) -> views Floor selection dialog -> selects Ground Floor -> views detailed floor plan with rooms and assets.
2. *Inspect Asset*: User taps a fire extinguisher or computer asset pin on a room -> bottom sheet opens displaying asset metadata and room assignment.

---

## 3. TERMINOLOGIES AND OOP APPLICATION

| Concept | Definition and application in this project |
| :--- | :--- |
| **Building / Room / Asset** | Domain entities representing physical campus structures, enclosed spaces, and mapped equipment. |
| **Encapsulation** | Implemented in data models (`Building`, `Room`, `Asset`, `FloorData`) using immutable `data class` structures, and via `by lazy` delegates in building plan objects to encapsulate heavy initialization logic. |
| **Abstraction** | Implemented through [`PlanBuilder`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.data.PlanBuilder) which hides complex pixel-to-fraction normalization math (`fx`, `fy`), and [`CampusData`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData) which abstracts away specific building plan implementations behind a unified `floorData()` call. |
| **Inheritance** | Applied through Jetpack Compose UI component hierarchies and layout containers (`ZoomPanBox`, `Scaffold`, `Box`), reusing rendering and gesture-handling behaviors. |
| **Polymorphism** | Utilized via enums (`AssetType`, `Floor`, `Category`, `BuildingStatus`) where different UI icons, colors, and behaviors are invoked polymorphically based on enum type across map and report screens. |
| **Object relationships** | Aggregation between `FloorData` and `Room`/`Asset` lists; Composition of shapes within `Building`; Association between `Asset` and its parent `Room`. |

---

## 4. OBJECT MODELLING AND UML DESIGN

### Class Summary Table
| Class Name | Purpose / Responsibility | Key Attributes | Key Methods |
| :--- | :--- | :--- | :--- |
| [`CampusViewModel`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.viewmodel.CampusViewModel) | Manages UI state, selected building, floor, zoom level, and search query. | `selectedBuilding`, `selectedFloor`, `zoom`, `searchQuery` | `selectBuilding()`, `selectFloor()`, `setZoom()` |
| [`CampusData`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.data.CampusData) | Repository providing building metadata and routing floor data requests. | `buildings`, `cache` | `floorData(Building, Floor)` |
| [`PlanBuilder`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.data.PlanBuilder) | Fluent builder pattern helper for constructing floor rooms, zones, and assets. | `rooms`, `zones`, `assets`, `aspect` | `room()`, `zone()`, `asset()`, `build()` |
| [`Building`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Building) | Represents a campus building structure. | `id`, `name`, `category`, `floorCount`, `shapes` | `hasPlan`, `roomsPerRow` |
| [`Room`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Room) | Represents an individual room inside a floor plan. | `id`, `buildingId`, `floor`, `name`, `x, y, w, h`, `computers` | (Data class getters) |
| [`Asset`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.model.Asset) | Represents a physical asset or safety fixture mapped on a floor. | `id`, `buildingId`, `floor`, `type`, `x, y`, `label`, `roomId` | (Data class getters) |

### UML Class Relationships
- **Association**: `CampusViewModel` interacts with `CampusData` to fetch floor details.
- **Composition / Aggregation**: `FloorData` owns a collection of `Room`, `Asset`, and `Zone` objects (`FloorData o-- Room`, `FloorData o-- Asset`).
- **Dependency**: `PlanBuilder` produces `FloorData` objects consumed by building plan singletons (`Building1Plan`, `Building3Plan`, etc.).

---

## 5. CLASS, ABSTRACT CLASS, AND INTERFACE SPECIFICATIONS

### 5.1 Concrete Class Specification (`PlanBuilder`)
| Item | Specification |
| :--- | :--- |
| **Class name / type | [`PlanBuilder`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.data.PlanBuilder) (Kotlin Class) |
| **Purpose / responsibility | Fluent helper class to simplify defining rooms, hallways, stairs, and assets using pixel coordinates. |
| **Superclass / interfaces | None |
| **Attributes** | `buildingId: Int`, `floor: Floor`, `prefix: String`, `x0: Float`, `y0: Float`, `pw: Float`, `ph: Float`, `rooms`, `zones`, `assets` |
| **Constructors** | `constructor(buildingId: Int, floor: Floor, prefix: String, x0: Float, y0: Float, pw: Float, ph: Float)` |
| **Methods** | `room(...)`, `zone(...)`, `asset(...)`, `build(): FloorData` |
| **Relationships** | Creates `Room`, `Zone`, and `Asset` instances, returning a unified `FloorData`. |
| **Encapsulation** | Internal mutable lists (`rooms`, `zones`, `assets`) are encapsulated during building and exported as immutable lists upon `.build()`. |

### 5.2 Polymorphism & Enum Specification (`AssetType`)
- The `AssetType` enum defines polymorphic item categories (`AIRCON`, `WIFI`, `CCTV`, `COMPUTER`, `PROJECTOR`, `FIRE_ALARM`, `EXTINGUISHER`, `HYDRANT`, `EXIT`, etc.). Each enum constant provides properties for its display label and icon resource, allowing the rendering engine in [`FloorPlanScreen.kt`](file:///C:/Users/KCignacio/AndroidStudioProjects/ARASOFcampusassetmapping/app/src/main/java/ph/edu/batstateu/arasof/arasofcampusassetmapping/ui/FloorPlanScreen.kt) to render distinct icons and tooltips dynamically without conditional type-checking boilerplate.

---

## 6. USER INTERFACE DESIGN

| Screen | Purpose | Controls / actions | Next screen |
| :--- | :--- | :--- | :--- |
| **CampusMapScreen** | Overview of campus layout & building selection | Zoom/pan gestures, building tap polygons, search bar | FloorPlanScreen |
| **FloorPlanScreen** | Detailed interior floor inspection | Floor selector tabs, zoom/pan box, room/asset pin taps | RoomSheet (bottom sheet) |
| **ReportsScreen** | Summary statistics of campus assets | Category filters, count summaries | N/A |

### UI-to-Class Mapping
- Tapping a building polygon on [`CampusMapScreen.kt`](file:///C:/Users/KCignacio/AndroidStudioProjects/ARASOFcampusassetmapping/app/src/main/java/ph/edu/batstateu/arasof/arasofcampusassetmapping/ui/CampusMapScreen.kt) invokes `CampusViewModel#selectBuilding(Building)`, updating the reactive stateflow.
- Selecting a floor tab triggers `CampusViewModel#selectFloor(Floor)`, which calls `CampusData#floorData()` to retrieve the corresponding `FloorData` object and render rooms via `PlanBuilder` generated models.

---

## 7. DATA DESIGN AND IMPLEMENTATION

- **Storage:** In-memory structured Kotlin objects (`CampusData`, `BuildingXPlan` objects) combined with resource drawables (`res/drawable/campus_map.png`).
- **Language / Framework:** Kotlin 2.0+, Jetpack Compose, Material 3, Android SDK.
- **Architecture:** Model-View-ViewModel (MVVM) architecture with unidirectional data flow using Kotlin `StateFlow`.

---

## 8. TESTING AND RESULTS

| Test ID | Test Data and Steps | Expected Result | Actual Result / Status |
| :--- | :--- | :--- | :--- |
| **TC-01** | Tap Building 3 on campus map | Opens floor selection dialog / transitions to floor plan | Pass |
| **TC-02** | Switch between Ground and 2nd floor tabs | Floor plan redraws with correct rooms and assets for that floor | Pass |
| **TC-03** | Tap an asset pin (e.g., Fire Extinguisher) | Opens `RoomSheet` displaying asset label and details | Pass |

- **OOP Verification:** Encapsulation and abstraction were verified through unit inspection of data immutability and builder pattern encapsulation. Polymorphic rendering of `AssetType` icons was verified across all building floor plans.

---

## 9. DEPLOYMENT AND USER GUIDE

- **System Requirements:** Android 8.0 (API 26) or higher, 2GB RAM minimum.
- **Installation & Launch:** Open project in Android Studio (Jellyfish or newer), sync Gradle, and run on an Android emulator or physical device.
- **Common Tasks:** Tap any building on the map to inspect its floor plans and view installed IT and safety assets.

---

## 10. CONCLUSION AND REFERENCES

### Conclusion
The **ARASOF Campus Asset Mapping System** successfully fulfills its design objectives by delivering an interactive, object-oriented digital campus mapping application. By applying rigorous OOP principles—such as encapsulation in data models, abstraction via [`PlanBuilder`](class://ph.edu.batstateu.arasof.arasofcampusassetmapping.data.PlanBuilder), and polymorphism through enums—the codebase remains clean, modular, and easily extensible.

### References
1. Android Developers Documentation. *Jetpack Compose UI & State Management*. https://developer.android.com/jetpack/compose
2. Gamma, E., et al. (1994). *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley.
3. Republic Act No. 9514. *Revised Fire Code of the Philippines of 2008* (Fire safety asset placement guidelines).
