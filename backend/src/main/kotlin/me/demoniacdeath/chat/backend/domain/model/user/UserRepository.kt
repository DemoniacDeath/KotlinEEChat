package me.demoniacdeath.chat.backend.domain.model.user

interface UserRepository {
    @Throws(UserNotFoundException::class)
    fun getByHanlderAndPassword(handler: String, passwordPlain: String): User

    @Throws(UserNotFoundException::class)
    fun getByToken(token: String): User
}