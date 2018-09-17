package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import me.demoniacdeath.chat.backend.domain.model.user.User
import javax.ws.rs.core.HttpHeaders
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
open class AuthenticationFilter : ContainerRequestFilter {
    @Inject
    lateinit var schemeParser: SchemeParser

    @Inject
    lateinit var authenticator: Authenticator

    @Inject
    @AuthenticatedUser
    lateinit var userAuthenticatedEvent: Event<User>

    override fun filter(requestContext: ContainerRequestContext) {
        val authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)

        val scheme = schemeParser.getSchemeForAuthenticationHeader(authorizationHeader)

        if (scheme == null) {
            abortWithUnauthorized(requestContext)
            return
        }

        try {
            val user = authenticator.authenticate(scheme, authorizationHeader)
            userAuthenticatedEvent.fire(user)
        } catch (e: AuthenticationException) {
            abortWithUnauthorized(requestContext)
        }
    }

    private fun abortWithUnauthorized(requestContext: ContainerRequestContext) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build())
    }
}