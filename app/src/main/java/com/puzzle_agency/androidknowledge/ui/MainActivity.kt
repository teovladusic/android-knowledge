package com.puzzle_agency.androidknowledge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.puzzle_agency.androidknowledge.knowledge.network.network_connection_status.NetworkConnectionStatusManager
import com.puzzle_agency.androidknowledge.ui.theme.AndroidKnowledgeTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkConnectionStatusManager: NetworkConnectionStatusManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionStatusManager.startListenNetworkState()

        setContent {
            AndroidKnowledgeTheme {
                // A surface container using the 'background' color from the theme
                val isConnected by networkConnectionStatusManager.isNetworkConnectedFlow.collectAsStateWithLifecycle()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Greeting(name = "Android Knowledge")
                        Text(
                            text = when (isConnected) {
                                true -> "Connected"
                                false -> "Not connected"
                                null -> "Cannot determine if connected"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
