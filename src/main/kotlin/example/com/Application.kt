package example.com

import example.com.features.login.configureLoginRouting
import example.com.features.register.configureRegisterRouting
import example.com.features.userinfo.configureQuestionsRouting
import example.com.features.userinfo.configureTokenRouting
import example.com.features.userinfo.configureUserInfoRouting
import example.com.plugins.configureRouting
import example.com.plugins.configureSerialization
import example.com.utils.DotEnvCfg
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Database.connect(
        url = DotEnvCfg.getEnv("DB_URL"),
        driver = DotEnvCfg.getEnv("DB_DRIVER"),
        user = DotEnvCfg.getEnv("DB_USER"),
        password = DotEnvCfg.getEnv("DB_PASSWORD")
    )
    configureRegisterRouting()
    configureLoginRouting()
    configureSerialization()
    configureRouting()
    configureUserInfoRouting()
    configureTokenRouting()
    configureQuestionsRouting()
}
