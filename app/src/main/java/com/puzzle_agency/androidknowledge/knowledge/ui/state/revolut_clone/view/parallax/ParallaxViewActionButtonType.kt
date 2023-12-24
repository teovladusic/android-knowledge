package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.parallax

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.ui.graphics.vector.ImageVector
import com.puzzle_agency.androidknowledge.R

enum class ParallaxViewActionButtonType(
    val imageVector: ImageVector,
    @StringRes val labelRes: Int
) {
    AddMoney(Icons.Default.Add, R.string.add_money),
    Conversion(Icons.Default.Repeat, R.string.conversion),
    Specifics(Icons.Default.Menu, R.string.specifics),
    More(Icons.Default.MoreHoriz, R.string.more)
}