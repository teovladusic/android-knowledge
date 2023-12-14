package com.puzzle_agency.androidknowledge.knowledge.network.network_connection_status

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NetworkConnectionStatusViewModel @Inject constructor(
    private val networkConnectionStatusManager: NetworkConnectionStatusManager
) : ViewModel() {

    init {
        networkConnectionStatusManager.startListenNetworkState()
    }

    private val _state = MutableStateFlow(NetworkConnectionStatusState())

    val state = _state.combine(
        networkConnectionStatusManager.isNetworkConnectedFlow
    ) { state, isNetworkConnected ->
        state.copy(isNetworkConnected = isNetworkConnected)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), NetworkConnectionStatusState())

    override fun onCleared() {
        super.onCleared()
        networkConnectionStatusManager.stopListenNetworkState()
    }
}

@Suppress("Unused")
@Composable
fun NetworkConnectionStatusManagerUi(viewModel: NetworkConnectionStatusViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        val text = when (state.isNetworkConnected) {
            true -> "Connected"
            false -> "Not connected"
            null -> "Loading..."
        }

        Text(text = text, modifier = Modifier.align(Alignment.Center))
    }
}

data class NetworkConnectionStatusState(
    val isNetworkConnected: Boolean? = null
)