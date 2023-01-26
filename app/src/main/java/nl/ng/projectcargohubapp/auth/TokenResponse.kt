package nl.ng.projectcargohubapp.auth

data class TokenResponse(
    var accessToken: String,
    var tokenType: String,
    var expiresIn: String
)
