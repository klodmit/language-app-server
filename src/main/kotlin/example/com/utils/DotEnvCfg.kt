package example.com.utils

import io.github.cdimascio.dotenv.Dotenv

object DotEnvCfg {

    private val dotenv: Dotenv by lazy {
        Dotenv.configure().ignoreIfMissing().load()
    }

    fun getEnv(key: String): String {
        return dotenv[key]
    }

}