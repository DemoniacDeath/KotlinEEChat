package me.demoniacdeath.chat.backend.interfaces.rest

import javax.ws.rs.GET
import javax.ws.rs.Path

@Path("")
class Index {
    @GET
    fun get(): String {
        return "Hello World"
    }
}