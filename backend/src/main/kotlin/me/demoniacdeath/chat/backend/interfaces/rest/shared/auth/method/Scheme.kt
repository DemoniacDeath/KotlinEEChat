package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.method

import me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.AuthenticationException
import me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.AuthenticatorInterface
import java.util.*
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.core.HttpHeaders

sealed class Scheme(private val name: String): AuthMethod {
    class Basic: Scheme("Basic") {
        override fun authenticate(containerRequestContext: ContainerRequestContext, authenticator: AuthenticatorInterface) {
            val header = extractAuthenticationHeader(containerRequestContext) ?: throw AuthenticationException()
            val fullString = String(Base64.getDecoder().decode(getAuthString(header)))
            authenticator.visitWithUsernameAndPassword(
                    fullString.substringBefore(':'),
                    fullString.substringAfter(':')
            )
        }
    }
    class Bearer: Scheme("Bearer") {
        override fun authenticate(containerRequestContext: ContainerRequestContext, authenticator: AuthenticatorInterface) {
            val header = extractAuthenticationHeader(containerRequestContext) ?: throw AuthenticationException()
            authenticator.visitWithToken(getAuthString(header))
        }
    }

    override fun isAppliableToContainerRequestContext(containerRequestContext: ContainerRequestContext): Boolean {
        val header = extractAuthenticationHeader(containerRequestContext) ?: return false
        return header.toLowerCase().startsWith((name.toLowerCase()) + " ")
    }

    protected fun extractAuthenticationHeader(requestContext: ContainerRequestContext): String? {
        return requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)
    }

    fun getAuthString(header: String): String {
        return header.substring(name.length).trim()
    }
}