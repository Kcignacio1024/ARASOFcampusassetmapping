package ph.edu.batstateu.arasof.arasofcampusassetmapping.domain.user

import ph.edu.batstateu.arasof.arasofcampusassetmapping.model.AssetType

/**
 * Abstract base class representing a system user.
 * Demonstrates Inheritance, Polymorphism, and Liskov Substitution Principle (LSP).
 */
sealed class AppUser(val name: String) {
    abstract val roleName: String
    abstract fun canResolveIncident(): Boolean
    abstract fun canSee(assetType: AssetType): Boolean
}

class Student(name: String) : AppUser(name) {
    override val roleName: String = "Student"
    override fun canResolveIncident(): Boolean = false
    override fun canSee(assetType: AssetType): Boolean = assetType.studentVisible
}

class MaintenanceStaff(name: String) : AppUser(name) {
    override val roleName: String = "Maintenance"
    override fun canResolveIncident(): Boolean = true
    override fun canSee(assetType: AssetType): Boolean = true
}
