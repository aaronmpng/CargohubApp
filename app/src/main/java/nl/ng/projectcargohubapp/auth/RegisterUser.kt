package nl.ng.projectcargohubapp.auth

data class RegisterUser(
    val email: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val phoneNumber: String,
    val companyId: String,
)
