package me.demoniacdeath.chat.backend.interfaces.rest.shared.auth

interface AuthenticatorInterface {
    fun visitWithToken(token: String)
    fun visitWithUsernameAndPassword(username: String, password: String)
}
