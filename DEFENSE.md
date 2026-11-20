# Student Defense Guide

## 1. Class Descriptions (What it does, Why it exists, OOP/SOLID principle)

1. **`AppUser` (Abstract Class) & Subclasses (`Student`, `MaintenanceStaff`)**
   - *What it does:* Represents system users with different privileges.
   - *Why it exists:* Eliminates primitive boolean role flags (`isMaintenance`) from UI and domain code.
   - *OOP/SOLID:* Demonstrates Inheritance, Polymorphism, and Liskov Substitution Principle (LSP).

2. **`FloorPlanProvider` (Interface)**
   - *What it exists for:* Defines a uniform contract (`forFloor`) implemented by all building plan singletons.
   - *OOP/SOLID:* Demonstrates Interface Segregation Principle (ISP) and Open-Closed Principle (OCP) via the registry map in `CampusData`.

3. **`IncidentRepository` (Interface) & `SqliteIncidentRepository` / `InMemoryIncidentRepository`**
   - *What it does:* Abstracts data persistence operations away from business logic and UI.
   - *OOP/SOLID:* Demonstrates Dependency Inversion Principle (DIP). ViewModel and services depend solely on the interface.

4. **`IncidentService` (Class)**
   - *What it does:* Encapsulates all incident business rules (one open report per asset, joining reports, resolution permissions).
   - *OOP/SOLID:* Demonstrates Single Responsibility Principle (SRP). ViewModel only handles UI state.

5. **`CampusViewModel` (Class)**
   - *What it does:* Retains lifecycle-aware UI state across configuration changes and coordinates interactions between UI and services.
   - *OOP/SOLID:* Separation of concerns in MVVM architecture.

6. **`IncidentDatabase` (Class)**
   - *What it does:* Manages local SQLite database creation, table upgrades, and record persistence.
   - *OOP/SOLID:* Encapsulation of database access.

## 2. Rubric Mapping Table

| Rubric Criterion | Max Pts | Demonstrating File / Class |
| :--- | :---: | :--- |
| **Functionality & Features** | 15 | `IncidentDatabase.kt`, `CampusMapScreen.kt`, `FloorPlanScreen.kt`, `ReportsScreen.kt` |
| **OOP Concepts** | 20 | `AppUser.kt` (`Student`, `MaintenanceStaff`), `Models.kt` (`Building`, `Asset`, `Incident`) |
| **SOLID Principles** | 15 | `IncidentRepository.kt` (DIP/ISP), `IncidentService.kt` (SRP), `FloorPlanProvider.kt` (OCP) |
| **Code Knowledge** | 15 | Clean modular package layout (`ui`, `viewmodel`, `domain`, `data`) |
| **Architecture** | 10 | Strict MVVM with decoupled domain services and repositories |
| **Android UI / UX** | 10 | Jetpack Compose Material 3, ZoomPanBox, RoomSheet bottom sheets, responsive dialogs |
| **Error Handling** | 10 | Safe try/catch and cursor usage in SQLite database |
| **Documentation** | 5 | `README.md` and `DEFENSE.md` |

## 3. Program Flow: Login -> Report -> Fix
1. **Login:** User enters name, selects role (Student or Maintenance/IT), and provides PIN if maintenance (`1234`). `CampusViewModel` instantiates the corresponding `AppUser` (`Student` or `MaintenanceStaff`) and navigates to the campus map.
2. **Report:** User taps a building -> selects floor -> taps room -> opens `RoomSheet` -> selects asset -> enters problem description -> submits. `IncidentService` checks the one-open-report rule (joining if already reported) and saves the incident via `IncidentRepository` to SQLite.
3. **Fix:** Maintenance user logs in -> opens `ReportsScreen` -> selects an open incident -> enters resolution note -> taps "Mark as fixed". `IncidentService` verifies `user.canResolveIncident()`, updates the status to `RESOLVED`, and persists changes.

## 4. Top 10 Professor Questions & Answers
1. *Q: How does the app ensure an asset only has one open report?*
   - A: `IncidentService#reportIssue` checks if an open report already exists for that asset ID. If so, it adds the reporter to `alsoReportedBy` rather than creating a duplicate.
2. *Q: How does your code adhere to the Open-Closed Principle (OCP)?*
   - A: Building plans implement `FloorPlanProvider` and are registered in a map inside `CampusData`. Adding a building requires zero changes to `CampusData#floorData()`.
3. *Q: Where are incidents stored when the app closes?*
   - A: Locally on the device in a SQLite database via `IncidentDatabase` and `SqliteIncidentRepository`.
4. *Q: How does polymorphism replace role checks?*
   - A: Instead of checking `if (isMaintenance)`, screens and services call `user.canResolveIncident()` or `user.canSee(assetType)`, allowing `Student` and `MaintenanceStaff` to polymorphicially define their behavior.
5. *Q: What design pattern is used for building plans?*
   - A: The Builder pattern (`PlanBuilder`) combined with Factory/Provider interfaces (`FloorPlanProvider`).
6. *Q: How is Dependency Inversion applied?*
   - A: `CampusViewModel` and `IncidentService` depend on the `IncidentRepository` interface, allowing SQLite and in-memory repositories to be swapped effortlessly (proven by JUnit tests).
7. *Q: How do you handle screen rotation?*
   - A: Jetpack Compose and `CampusViewModel` retain UI state across configuration changes automatically.
8. *Q: Task: Add a new user type (e.g. Guest).*
   - A: Create `class Guest(name: String) : AppUser(name)` implementing `canResolveIncident() { false }` and `canSee(...)`.
9. *Q: Task: Add a new asset type.*
   - A: Add the constant to the `AssetType` enum in `Models.kt` with its label, symbol, and visibility rules.
10. *Q: Task: Add a new building plan.*
    - A: Create `Building22Plan.kt` implementing `FloorPlanProvider`, and register it in `CampusData`.
