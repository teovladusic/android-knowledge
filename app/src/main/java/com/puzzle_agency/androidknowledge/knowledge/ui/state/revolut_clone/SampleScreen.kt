package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.state_holder.SampleViewModel
import com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.SampleView

@Composable
fun SampleScreen(viewModel: SampleViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SampleView.Compose(state = state, events = viewModel.event, executor = viewModel)
}
