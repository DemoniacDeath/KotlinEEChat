package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import me.demoniacdeath.chat.backend.domain.model.user.User
import me.demoniacdeath.chat.backend.domain.model.user.UserRepository
import javax.enterprise.context.RequestScoped
import javax.enterprise.event.Observes
import javax.enterprise.inject.Produces
import javax.inject.Inject

@RequestScoped
class AuthenticatedUserProducer {
    @Produces
    @RequestScoped
    @AuthenticatedUser
    private lateinit var authenticatedUser: User

    fun handleAuthenticatedUser(@Observes @AuthenticatedUser user: User) {
        authenticatedUser = user
    }
}