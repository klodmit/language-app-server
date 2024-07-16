package example.com

import example.com.features.login.configureLoginRouting
import example.com.features.register.configureRegisterRouting
import example.com.plugins.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    Database.connect(
        "jdbc:postgresql://languagepg-klodmit.amvera.io:5432/languageapp", driver = "org.postgresql.Driver",
        user = "klodmit", password = "228972Gfg"
    )

    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRegisterRouting()
    configureLoginRouting()
    configureSerialization()
    configureRouting()
}
