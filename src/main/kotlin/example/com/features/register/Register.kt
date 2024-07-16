package example.com.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val login: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val points: Int

)

@Serializable
data class RegisterResponseRemote(
    val token: String
)