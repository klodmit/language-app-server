package example.com.features.register

import encodePass
import example.com.database.tokens.TokensDTO
import example.com.database.userinfo.UserInfo
import example.com.database.userinfo.UserInfoDTO
import example.com.database.users.Tokens
import example.com.database.users.UserDTO
import example.com.database.users.Users
import example.com.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import mysalt
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, message = "Email is not valid")
        } else if (userDTO != null) {
            call.respond(HttpStatusCode.BadRequest, message = "User already exists")
        } else {
            val token = UUID.randomUUID().toString()
            Users.insert(
                UserDTO(
                    login = registerReceiveRemote.login,
                    password = registerReceiveRemote.password.encodePass(mysalt),
                )
            )
            UserInfo.insert(
                UserInfoDTO(
                    login = registerReceiveRemote.login,
                    firstname = registerReceiveRemote.firstname,
                    lastname = registerReceiveRemote.lastname,
                    email = registerReceiveRemote.email,
                    points = registerReceiveRemote.points
                )
            )

            Tokens.insert(
                TokensDTO(
                    rowid = UUID.randomUUID().toString(), login = registerReceiveRemote.login,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }
    }
}