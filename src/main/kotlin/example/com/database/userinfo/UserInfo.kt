package example.com.database.userinfo


import example.com.database.users.UserDTO
import example.com.database.users.Users
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object UserInfo : Table("userinfo") {
    private val login = UserInfo.varchar("login", 25)
    private val firstname = UserInfo.varchar("firstname", 30)
    private val lastname = UserInfo.varchar("lastname", 30)
    private val email = UserInfo.varchar("email", 30)
    private val points = UserInfo.integer("points")

    fun insert(userInfoDTO: UserInfoDTO) {
        transaction {
            UserInfo.insert {
                it[login] = userInfoDTO.login
                it[firstname] = userInfoDTO.firstname
                it[lastname] = userInfoDTO.lastname
                it[email] = userInfoDTO.email
                it[points] = userInfoDTO.points
            }
        }
    }

    fun fetchUser(login: String): UserInfoDTO? {
        return try {
            transaction {
                val userModel = UserInfo.select { UserInfo.login.eq(login) }.single()
                UserInfoDTO(
                    login = userModel[UserInfo.login],
                    firstname = userModel[UserInfo.firstname],
                    lastname = userModel[UserInfo.lastname],
                    email = userModel[UserInfo.email],
                    points = userModel[UserInfo.points]
                )
            }
        } catch (e: Exception) {
            null
        }
    }

}