package example.com.features.userinfo

import example.com.database.tokens.TokensDTO
import example.com.database.userinfo.UserInfo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class UserInfoController(private val call: ApplicationCall) {
    suspend fun getUserInfo() {
        val receive = call.receive<UserInfoReceiveRemote>()
        val userInfoDTO = UserInfo.fetchUser(receive.login)
        if (userInfoDTO != null) {
            call.respond(
                UserInfoResponseRemote(
                    login = receive.login,
                    firstname = userInfoDTO.firstname,
                    lastname = userInfoDTO.lastname,
                    email = userInfoDTO.email,
                    points = userInfoDTO.points
                )
            )
        }
    }
}
