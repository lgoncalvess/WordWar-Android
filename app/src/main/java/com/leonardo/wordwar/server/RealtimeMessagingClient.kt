package com.leonardo.wordwar.server

import kotlinx.coroutines.flow.Flow

interface RealtimeMessagingClient {
    fun getStateStream(): Flow<String>
    suspend fun sendAction(action: String)
    suspend fun close()
}