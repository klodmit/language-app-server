package example.com.features.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginReceiveRemote(
    val login: String,
    val password: String,
)

@Serializable
data class LoginResponseRemote(
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val points: Int,
    val token: String
)
