package example.com.features.validtoken

import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    val token: String
)

@Serializable
data class TokenInfoResponse(
    val id: String,
    val login: String,
    val token: String,
)