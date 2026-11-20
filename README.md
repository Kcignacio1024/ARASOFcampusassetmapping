# ARASOF Campus Asset Mapping & Problem Reporting System

## Project Overview
The ARASOF Campus Asset Mapping & Problem Reporting System is an interactive Android application built with Kotlin and Jetpack Compose for Batangas State University - ARASOF. It maps campus buildings, floor plans, rooms, and critical safety/IT assets, empowering students and maintenance staff to report and track facility issues.

## Objectives & Features
- **Interactive Campus Map:** Zoomable/pannable map with building polygons and maintenance badges.
- **Multi-Floor Building Inspector:** Switch between floors to inspect rooms and distinct asset tags (e.g., `B23-G-HALL-ID-03`).
- **Asset Problem Reporting ("Incidents"):** One open report per asset rule; multiple users can join an existing report ("I have this problem too").
- **Maintenance Operations:** Role-based access (Student vs MaintenanceStaff via polymorphism and LSP), incident resolution, and offline SQLite persistence.
- **Fuzzy Building Search:** Instant building search with alias matching and "Did you mean" suggestions.

## How to Run
1. Open the project in Android Studio (Jellyfish or newer).
2. Sync Gradle dependencies.
3. Run unit tests: `./gradlew testDebugUnitTest`.
4. Run the app on an Android emulator or physical device (Min SDK 26+).

## Architecture & Layers
- `ui`: Jetpack Compose screens and dialogs.
- `viewmodel`: Lifecycle-aware state management (`CampusViewModel`).
- `domain`: Core domain models (`AppUser`, `Building`, `Asset`, `Incident`) and business rules (`IncidentService`).
- `data`: Repositories (`SqliteIncidentRepository`, `InMemoryIncidentRepository`), SQLite database (`IncidentDatabase`), and building plan providers (`FloorPlanProvider` registry).

## One-Open-Report Rule
Each physical asset can have at most one `OPEN` incident at a time. If another user reports a problem on the same asset, they automatically join the existing incident ("I have this problem too").

## Known Limitations
- Authentication uses a demo PIN (`1234`) and local username input rather than centralized cloud identity providers.
- Incident reports are stored locally on the device SQLite database.
