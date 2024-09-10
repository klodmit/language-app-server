package example.com.features.userinfo


import example.com.features.validtoken.TokenController
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureTokenRouting() {
    routing {
        post("/validtoken") {
            val tokenController = TokenController(call)
            tokenController.validateToken()
        }
    }
}