package example.com.features.userinfo

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoReceiveRemote(
    val login: String
)

@Serializable
data class UserInfoResponseRemote(
    val login: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val points: Int
)