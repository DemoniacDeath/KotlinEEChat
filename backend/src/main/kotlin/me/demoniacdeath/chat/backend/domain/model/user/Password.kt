package me.demoniacdeath.chat.backend.domain.model.user

data class Password (var passwordHash: String, var salt: String)
