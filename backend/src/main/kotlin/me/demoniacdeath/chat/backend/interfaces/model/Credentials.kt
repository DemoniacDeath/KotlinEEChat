package me.demoniacdeath.chat.backend.interfaces.model

import me.demoniacdeath.chat.backend.Bean
import java.io.Serializable

@Bean
class Credentials(
        var handler: String,
        var password: String
): Serializable