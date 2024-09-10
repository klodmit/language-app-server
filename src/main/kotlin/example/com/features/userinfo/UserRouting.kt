package example.com.features.userinfo


import example.com.database.userinfo.UserInfo
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserInfoRouting() {
    routing {
        post("/getUser") {
            val userInfoController = UserInfoController(call)
            userInfoController.getUserInfo()
        }
        post("/updateUserInfo") {
            val userInfoController = UserInfoController(call)
            userInfoController.updateUserInfo()
        }
        post("/updateUserScore") {
            val userInfoController = UserInfoController(call)
            userInfoController.updateUserScore()
        }
        get("/getTopThreeUsers") {
            val topUsers = UserInfo.getTopThreeUsers()
            call.respond(topUsers) // Возвращаем результат
        }

    }
}