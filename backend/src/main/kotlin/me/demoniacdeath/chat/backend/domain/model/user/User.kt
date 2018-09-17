package me.demoniacdeath.chat.backend.domain.model.user

import me.demoniacdeath.chat.backend.Bean
import java.security.Principal

@Bean
class User (
        open var userId: UserId,
        open var userHandler: UserHandler,
        open var userPassword: Password,
        open var userName: UserName
) : Principal {
    override fun getName(): String = userHandler.handler
}