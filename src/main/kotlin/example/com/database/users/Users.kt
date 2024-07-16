package example.com.database.users

import org.jetbrains.exposed.sql.Except
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table("users") {
    private val login = Users.varchar("login", 25)
    private val password = Users.varchar("password", 25)
    private val firstname = Users.varchar("firstname", 30)
    private val lastname = Users.varchar("lastname", 30)
    private val email = Users.varchar("email", 30)
    private val points = Users.integer("points")


    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[firstname] = userDTO.firstname
                it[lastname] = userDTO.lastname
                it[email] = userDTO.email
                it[points] = userDTO.points

            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.select { Users.login.eq(login) }.single()
                UserDTO(
                    login = userModel[Users.login],
                    password = userModel[Users.password],
                    firstname = userModel[Users.firstname],
                    lastname = userModel[Users.lastname],
                    email = userModel[Users.email],
                    points = userModel[Users.points]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}