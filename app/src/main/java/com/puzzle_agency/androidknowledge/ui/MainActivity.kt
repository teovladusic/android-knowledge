package com.puzzle_agency.androidknowledge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleViewModel
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.SampleView
import com.puzzle_agency.androidknowledge.ui.theme.AndroidKnowledgeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sampleViewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AndroidKnowledgeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by sampleViewModel.state.collectAsStateWithLifecycle()
                    SampleView.Compose(
                        state = state,
                        events = sampleViewModel.event,
                        executor = sampleViewModel
                    )
                }
            }
        }
    }
}
