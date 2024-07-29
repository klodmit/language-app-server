package example.com.features.userinfo


import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureUserInfoRouting() {
    routing {
        post("/getUser") {
            val userInfoController = UserInfoController(call)
            userInfoController.getUserInfo()
        }
    }
}