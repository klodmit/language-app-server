package example.com.features.login

import example.com.cache.InMemoryCache
import example.com.cache.TokenCache
import example.com.database.tokens.TokensDTO
import example.com.database.users.Tokens
import example.com.database.users.UserDTO
import example.com.database.users.Users
import example.com.features.register.RegisterResponseRemote
import example.com.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin() {
        val recieve = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(recieve.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == recieve.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokensDTO(
                        rowid = UUID.randomUUID().toString(), login = recieve.login,
                        token = token
                    )
                )
                call.respond(LoginResponseRemote(
                    login = recieve.login,
                    password = recieve.password,
                    firstname = userDTO.firstname,
                    lastname = userDTO.lastname,
                    email = userDTO.email,
                    points = userDTO.points,
                    token = token
                ))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}