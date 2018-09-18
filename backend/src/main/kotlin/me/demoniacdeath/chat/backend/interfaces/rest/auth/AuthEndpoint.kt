package me.demoniacdeath.chat.backend.interfaces.rest.auth

import me.demoniacdeath.chat.backend.domain.model.user.User
import me.demoniacdeath.chat.backend.domain.model.user.UserRepository
import me.demoniacdeath.chat.backend.interfaces.model.Credentials
import java.lang.Exception
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("auth")
class AuthEndpoint {
    @Inject
    lateinit var userRepository: UserRepository

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun authenticateUser(
        @FormParam("handler") handler: String?,
        @FormParam("password") password: String?
    ) : Response
    {
        return try {
            val token: String = issueToken(userRepository.getByHanlderAndPassword(handler!!, password!!))
            Response.ok(token).build()
        } catch (e: Exception) {
            Response.status(Response.Status.FORBIDDEN).build()
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun authenticateCredentials(credentials: Credentials): Response {
        return try {
            val token: String = issueToken(userRepository.getByHanlderAndPassword(credentials.handler, credentials.password))
            Response.ok(token).build()
        } catch (e: Exception) {
            Response.status(Response.Status.FORBIDDEN).build()
        }
    }

    fun issueToken(user: User): String {
        return "WUBBALUBBADUBDUB"
    }
}