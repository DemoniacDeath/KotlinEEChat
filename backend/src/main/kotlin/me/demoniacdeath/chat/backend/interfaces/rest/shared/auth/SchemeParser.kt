package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

import me.demoniacdeath.chat.backend.Bean

@Bean
class SchemeParser {
    companion object {
        val schemes: List<Scheme> = listOf(
                Scheme.Basic(),
                Scheme.Bearer()
        )
    }

    fun getSchemeForAuthenticationHeader(header: String): Scheme? {
        for (scheme in schemes) {
            if (scheme.isAppliableToAuthenticationHeader(header)) {
                return scheme
            }
        }
        return null
    }
}