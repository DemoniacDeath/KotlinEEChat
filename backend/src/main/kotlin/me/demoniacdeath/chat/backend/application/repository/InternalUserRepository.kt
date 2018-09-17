package me.demoniacdeath.chat.backend.application.repository

import me.demoniacdeath.chat.backend.Bean
import me.demoniacdeath.chat.backend.application.utils.PasswordUtil
import me.demoniacdeath.chat.backend.domain.model.user.*
import javax.inject.Inject

const val DEFAULT_TOKEN = "WUBBALUBBADUBDUB"

class InternalUserRepository : UserRepository {
    @Inject
    lateinit var passwordUtil: PasswordUtil

    private val defaultUser: User = User(
        userId = UserId(666),
        userHandler = UserHandler("DemoniacDeath"),
        userPassword = Password("WUBBALUBBADUBDUB", "OLOLO"),
        userName = UserName("Michael", "Skvortsov")
    )

    override fun getByHanlderAndPassword(handler: String, passwordPlain: String): User {

        if (//TODO
                defaultUser.userHandler.handler == handler &&
                defaultUser.userPassword.passwordHash == passwordPlain
        ) {
            return defaultUser
        } else {
            throw UserNotFoundException()
        }
    }

    override fun getByToken(token: String): User {
        if (//TODO
                token == DEFAULT_TOKEN
        ) {
            return defaultUser
        } else {
            throw UserNotFoundException()
        }
    }
}