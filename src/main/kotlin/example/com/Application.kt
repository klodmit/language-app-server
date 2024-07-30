package example.com

import example.com.features.login.configureLoginRouting
import example.com.features.register.configureRegisterRouting
import example.com.features.userinfo.configureUserInfoRouting
import example.com.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {


    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Database.connect(
        "jdbc:postgresql://amvera-klodmit-cnpg-languagebase-rw/languageapp", driver = "org.postgresql.Driver",
        user = "klodmit", password = "228972"
    )
    configureRegisterRouting()
    configureLoginRouting()
    configureSerialization()
    configureRouting()
    configureUserInfoRouting()
}
