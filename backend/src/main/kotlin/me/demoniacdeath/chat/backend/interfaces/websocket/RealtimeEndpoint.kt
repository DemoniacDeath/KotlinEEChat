package me.demoniacdeath.chat.backend.interfaces.websocket

import javax.websocket.OnClose
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@ServerEndpoint("/realtime")
class RealtimeEndpoint {
    val sessions: MutableSet<Session> = HashSet()
    @OnOpen
    fun onOpen(session: Session) {
        session.maxIdleTimeout = 5 * 60 * 1000
        sessions.add(session)
    }

    @OnClose
    fun onClose(session: Session) {
        sessions.remove(session)
    }
}