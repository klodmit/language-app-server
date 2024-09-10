package example.com.features.userinfo

import example.com.database.tokens.TokensDTO
import example.com.database.userinfo.UserInfo
import example.com.database.userinfo.UserInfo.getTopThreeUsers
import example.com.database.userinfo.UserInfoDTO
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

    suspend fun updateUserScore() {
        val receive = call.receive<UserUpdateScoreRemote>()
        UserInfo.updateScore(receive.login, receive.points)
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


    suspend fun updateUserInfo() {
        val receive = call.receive<UserUpdateReceiveRemote>()
        UserInfo.update(
            UserInfoDTO(
                login = receive.login,
                firstname = receive.firstname,
                lastname = receive.lastname,
                email = receive.email,
                points = receive.points
            )
        )
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
