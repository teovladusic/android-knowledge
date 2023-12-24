package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.widget

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.puzzle_agency.androidknowledge.R

enum class CardWidgetCardType(@StringRes val labelRes: Int, val cardColor: Color) {
    Disposable(labelRes = R.string.disposable, cardColor = Color(color = 0xFFcf4873)),
    General(labelRes = R.string.general, cardColor = Color(color = 0xFF383A3C)),
    Virtual(labelRes = R.string.virtual, cardColor = Color(color = 0xFF0083c2)),
}