package example.com.database.users

import example.com.database.tokens.TokensDTO
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction


object Tokens : Table("tokens") {
    private val id = Tokens.varchar("id", 50)
    private val login = Tokens.varchar("login", 25)
    private val token = Tokens.varchar("token", 75)

    fun insert(tokensDTO: TokensDTO) {
        transaction {
            Tokens.insert {
                it[id] = tokensDTO.rowid
                it[login] = tokensDTO.login
                it[token] = tokensDTO.token

            }
        }
    }

}