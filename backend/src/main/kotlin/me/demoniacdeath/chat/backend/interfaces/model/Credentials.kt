package me.demoniacdeath.chat.backend.interfaces.model

import me.demoniacdeath.chat.backend.Bean
import java.io.Serializable

@Bean
class Credentials(
        open var handler: String,
        open var password: String
): Serializable