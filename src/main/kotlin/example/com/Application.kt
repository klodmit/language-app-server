package example.com

import example.com.features.login.configureLoginRouting
import example.com.features.register.configureRegisterRouting
import example.com.features.userinfo.configureTokenRouting
import example.com.features.userinfo.configureUserInfoRouting
import example.com.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {


    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Database.connect(
        url = environment.config.property("storage.jdbcURL").getString(),
        driver = environment.config.property("storage.driverClassName").getString(),
        user = environment.config.property("storage.user").getString(),
        password = environment.config.property("storage.password").getString()
    )
    configureRegisterRouting()
    configureLoginRouting()
    configureSerialization()
    configureRouting()
    configureUserInfoRouting()
    configureTokenRouting()
}
