package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import me.demoniacdeath.chat.backend.domain.model.user.User
import me.demoniacdeath.chat.backend.domain.model.user.UserNotFoundException
import me.demoniacdeath.chat.backend.domain.model.user.UserRepository
import javax.enterprise.context.RequestScoped
import javax.enterprise.event.Event
import javax.inject.Inject

class Authenticator : AuthenticatorInterface {
    @Inject
    lateinit var userRepository: UserRepository

    var user: User? = null

    fun authenticate(scheme: Scheme, header: String): User {
        user = null
        scheme.authenticate(header, this)
        return user ?: throw AuthenticationException()
    }

    override fun visitWithUsernameAndPassword(username: String, password: String) {
        try {
            user = userRepository.getByHanlderAndPassword(username, password)
        } catch (e: UserNotFoundException) {
            throw AuthenticationException()
        }
    }

    override fun visitWithToken(token: String) {
        try {
            user = userRepository.getByToken(token)
        } catch (e: UserNotFoundException) {
            throw AuthenticationException()
        }
    }
}