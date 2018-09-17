package me.demoniacdeath.chat.backend.interfaces.model

import java.io.Serializable

data class Credentials(
        val username: String,
        val password: String
): Serializable