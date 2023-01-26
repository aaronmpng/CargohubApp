package nl.ng.projectcargohubapp

data class AuthState(
    val isLoading: Boolean = false,
    val signUpEmail: String = "",
    val signUpPassword: String = "",
    val signInEmail: String = "",
    val signInPassword: String = ""
)
