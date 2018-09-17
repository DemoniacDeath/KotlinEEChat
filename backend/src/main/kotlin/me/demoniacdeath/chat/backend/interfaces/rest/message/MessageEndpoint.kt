package me.demoniacdeath.chat.backend.interfaces.rest.message

import me.demoniacdeath.chat.backend.domain.model.user.User
import me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.AuthenticatedUser
import me.demoniacdeath.chat.backend.interfaces.rest.shared.auth.Secured
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("message")
class MessageEndpoint {
    @Inject
    @AuthenticatedUser
    private lateinit var user: User

    @GET
    @Path("all")
    fun getAllMessages(): String {
        return "Hello World"
    }

    @GET
    @Path("user")
    @Secured
    fun getUserMessages(): String {
        return "Hello ${user.userHandler.handler}"
    }
}