package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import java.util.*

sealed class Scheme(private val name: String) {
    class Basic: Scheme("Basic") {
        override fun authenticate(header: String, authenticator: AuthenticatorInterface) {
            val fullString = String(Base64.getDecoder().decode(getAuthString(header)))
            authenticator.visitWithUsernameAndPassword(
                    fullString.substringBefore(':'),
                    fullString.substringAfter(':')
            )
        }
    }
    class Bearer: Scheme("Bearer") {
        override fun authenticate(header: String, authenticator: AuthenticatorInterface) {
            authenticator.visitWithToken(getAuthString(header))
        }
    }

    fun isAppliableToAuthenticationHeader(header: String): Boolean {
        return header.toLowerCase().startsWith((name.toLowerCase()) + " ")
    }
    fun getAuthString(header: String): String {
        return header.substring(name.length).trim()
    }
    abstract fun authenticate(header: String, authenticator: AuthenticatorInterface)
}