package example.com.features.login

import example.com.utils.decodePass
import example.com.database.tokens.TokensDTO
import example.com.database.users.Tokens
import example.com.database.users.Users
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import example.com.utils.mySalt
import java.util.*

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin() {
        val recieve = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(recieve.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            val pass = userDTO.password
            val decodedPass = decodePass(recieve.password, mySalt, pass)
            if (decodedPass == recieve.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokensDTO(
                        rowid = UUID.randomUUID().toString(), login = recieve.login,
                        token = token
                    )
                )
                call.respond(
                    LoginResponseRemote(
                        login = recieve.login,
                        password = userDTO.password,
                        token = token
                    )
                )
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}