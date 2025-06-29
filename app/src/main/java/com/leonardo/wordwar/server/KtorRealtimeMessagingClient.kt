package com.leonardo.wordwar.server

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull

class KtorRealtimeMessagingClient(
    private val client: HttpClient
) : RealtimeMessagingClient {
    private var session: WebSocketSession? = null

    override fun getStateStream(): Flow<String> {
        return flow {
            session = client.webSocketSession(
                HttpMethod.Get,
                "10.0.2.2",
                8080,
                "ws"
            )
            val messageStates = session!!
                .incoming
                .consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .mapNotNull {
                    it.readText()
                }

            emitAll(messageStates)
        }
    }

    override suspend fun sendAction(action: String) {
        session?.outgoing?.send(
            Frame.Text(action)
        )
    }

    override suspend fun close() {
        session?.close()
        session = null
    }
}