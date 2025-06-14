package com.leonardo.wordwar.server

import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.websocket.WebSockets
object ServiceLocator {

    private val httpClient = HttpClient(OkHttp) {
        install(WebSockets)
    }

    val messagingClient: RealtimeMessagingClient by lazy {
        KtorRealtimeMessagingClient(httpClient)
    }

}

