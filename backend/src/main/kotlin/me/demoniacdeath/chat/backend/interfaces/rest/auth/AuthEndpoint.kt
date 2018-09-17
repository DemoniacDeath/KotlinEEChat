package me.demoniacdeath.chat.backend.interfaces.rest.auth

import me.demoniacdeath.chat.backend.interfaces.model.Credentials
import java.lang.Exception
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("auth")
class AuthEndpoint {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    fun authenticateUser(
        @FormParam("username") username: String,
        @FormParam("password") password: String
    ) : Response
    {
        return try {
            authenticate(username, password)
            val token: String = issueToken(username)
            Response.ok(token).build()
        } catch (e: Exception) {
            Response.status(Response.Status.FORBIDDEN).build()
        }
    }

    fun authenticate(username: String, password: String) {
        if (
                username == "DemoniacDeath" &&
                password == "666"
        ) {
            return
        } else {
            throw Exception("Invalid username or password")
        }
    }

    fun issueToken(username: String): String {
        return "WUBBALUBBADUBDUB"
    }
}