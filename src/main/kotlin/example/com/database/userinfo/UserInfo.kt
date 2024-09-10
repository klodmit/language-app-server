package example.com.database.userinfo


import example.com.database.users.UserDTO
import example.com.database.users.Users
import example.com.features.userinfo.UserUpdateScoreRemote
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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

    fun update(userInfoDTO: UserInfoDTO){
        transaction {
            UserInfo.update({UserInfo.login eq userInfoDTO.login}){
                it[firstname] = userInfoDTO.firstname
                it[lastname] = userInfoDTO.lastname
                it[email] = userInfoDTO.email
                it[points] = userInfoDTO.points
            }
        }
    }

    fun updateScore(userLogin: String, newPoints: Int){
        return transaction {
            UserInfo.update({ UserInfo.login eq userLogin }) {
                it[points] = newPoints
            }
        }
    }

    fun getTopThreeUsers(): List<UserUpdateScoreRemote> {
        return transaction {
            UserInfo
                .slice(UserInfo.login, UserInfo.points)
                .selectAll()
                .orderBy(UserInfo.points, SortOrder.DESC )
                .orderBy(UserInfo.login, SortOrder.ASC)
                .limit(3)
                .map {
                    UserUpdateScoreRemote(
                        login = it[UserInfo.login],
                        points = it[UserInfo.points]
                    )
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