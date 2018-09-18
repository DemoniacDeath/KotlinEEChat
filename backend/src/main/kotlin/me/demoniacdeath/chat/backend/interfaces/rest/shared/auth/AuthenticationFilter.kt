package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import me.demoniacdeath.chat.backend.domain.model.user.User
import me.demoniacdeath.chat.backend.domain.model.user.UserNotFoundException
import me.demoniacdeath.chat.backend.domain.model.user.UserRepository
import me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.method.AuthMethod
import me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.method.Param
import me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.method.Scheme
import javax.ws.rs.container.ContainerRequestContext
import javax.annotation.Priority
import javax.enterprise.event.Event
import javax.inject.Inject
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.Priorities
import javax.ws.rs.core.Response
import javax.ws.rs.ext.Provider


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
open class AuthenticationFilter : ContainerRequestFilter, AuthenticatorInterface {
    companion object {
        val schemes: List<AuthMethod> = listOf(
                Scheme.Basic(),
                Scheme.Bearer(),
                Param("access_token"),
                Param("auth_token"),
                Param("token")
        )
    }

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    @AuthenticatedUser
    lateinit var userAuthenticatedEvent: Event<User>

    override fun filter(requestContext: ContainerRequestContext) {

        try {
            val authMethod = getSchemeForContainerRequestContext(requestContext) ?: throw AuthenticationException()
            authMethod.authenticate(requestContext, this)
        } catch (e: AuthenticationException) {
            abortWithUnauthorized(requestContext)
        }
    }

    private fun abortWithUnauthorized(requestContext: ContainerRequestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
    }
    private fun getSchemeForContainerRequestContext(requestContext: ContainerRequestContext): AuthMethod? {
        for (scheme in schemes) {
            if (scheme.isAppliableToContainerRequestContext(requestContext)) {
                return scheme
            }
        }
        return null
    }

    override fun visitWithUsernameAndPassword(username: String, password: String) {
        try {
            val user = userRepository.getByHanlderAndPassword(username, password)
            userAuthenticatedEvent.fire(user)
        } catch (e: UserNotFoundException) {
            throw AuthenticationException()
        }
    }

    override fun visitWithToken(token: String) {
        try {
            val user = userRepository.getByToken(token)
            userAuthenticatedEvent.fire(user)
        } catch (e: UserNotFoundException) {
            throw AuthenticationException()
        }
    }
}
