package me.demoniacdeath.chat.backend.domain.model.user

data class UserName(
        val firstName: String,
        val lastName: String
) {
    override fun toString() = "$firstName $lastName"
}
