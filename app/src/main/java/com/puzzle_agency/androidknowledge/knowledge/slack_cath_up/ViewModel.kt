package com.puzzle_agency.androidknowledge.knowledge.slack_cath_up

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.internal.toImmutableList
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _cards = MutableStateFlow(
        listOf(
            Card(Color(0xFFd77b5c), UUID.randomUUID().toString(), 1),
            Card(Color(0xFF1190cc), UUID.randomUUID().toString(), 2),
            Card(Color(0xFFd77b5c), UUID.randomUUID().toString(), 3),
            Card(Color(0xFF659117), UUID.randomUUID().toString(), 4),
            Card(Color(0xFFfbccb4), UUID.randomUUID().toString(), 5),
            Card(Color(0xFFe6b030), UUID.randomUUID().toString(), 6),
        )
    )
    val cards = _cards.asStateFlow()

    fun removeFirstCard() {
        _cards.update {
            val new = it.toMutableList()
            new.removeAt(0)
            new.toImmutableList()
        }
    }
}

data class Card(val color: Color, val id: String, val number: Int)
