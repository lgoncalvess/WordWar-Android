package com.leonardo.wordwar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonardo.wordwar.server.ServiceLocator
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val messagingClient = ServiceLocator.messagingClient

    fun observeMessages() {
        viewModelScope.launch {
            messagingClient.getStateStream().collect { message ->
                println("Received: $message")
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            messagingClient.sendAction(message)
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            messagingClient.close()
        }
    }
}