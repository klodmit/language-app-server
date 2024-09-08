package example.com.features.validtoken

import example.com.database.tokens.TokensDTO
import example.com.database.users.Tokens
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class TokenController(private val call: ApplicationCall) {
    suspend fun validateToken(){
        val receive = call.receive<TokenInfo>()
        val tokensDTO = Tokens.fetchToken(receive.token)
        if (tokensDTO != null) {
            call.respond(
                TokenInfoResponse(
                    login = tokensDTO.login
                )
            )
        }
    }
}