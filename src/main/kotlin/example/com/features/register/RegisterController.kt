package example.com.features.register

import example.com.cache.InMemoryCache
import example.com.cache.TokenCache
import example.com.database.tokens.TokensDTO
import example.com.database.users.Tokens
import example.com.database.users.UserDTO
import example.com.database.users.Users
import example.com.utils.isValidEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, message = "Email is not valid")
        }

        val userDTO = Users.fetchUser(registerReceiveRemote.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.BadRequest, message = "User already exists")
        } else {
            val token = UUID.randomUUID().toString()

            Users.insert(
                UserDTO(
                    login = registerReceiveRemote.login,
                    password = registerReceiveRemote.password,
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