package me.demoniacdeath.chat.backend.interfaces.websocket

import java.io.*
import java.util.*
import javax.json.Json
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

val sessions: MutableSet<Session> = Collections.synchronizedSet(HashSet())

@ServerEndpoint("/")
class RealtimeEndpoint {
    @OnOpen
    fun onOpen(session: Session) {
        session.maxIdleTimeout = 5 * 60 * 1000
        sessions.add(session)
    }

    @OnMessage
    fun onMessage(input: String) {
        val inputReader = StringReader(input)
        val parser = Json.createParser(inputReader)
        val outputWriter = StringWriter()
        val generator = Json.createGenerator(outputWriter)
        parser.next()
        val inputMessage = parser.string
        val outputMessage = "Hello, $inputMessage"
        generator.write(outputMessage).close()
        val output = outputWriter.toString()
        synchronized(sessions) {
            sessions.forEach {
                it.basicRemote.sendText(output)
            }
        }
    }

    @OnClose
    fun onClose(session: Session) {
        sessions.remove(session)
    }
}