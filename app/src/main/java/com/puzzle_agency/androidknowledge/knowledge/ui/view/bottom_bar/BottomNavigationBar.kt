package com.puzzle_agency.androidknowledge.knowledge.ui.view.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    navigationSelectedItem: Int,
    onClick: (item: BottomNavigationItem, index: Int) -> Unit
) {
    NavigationBar {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = { Text(navigationItem.label) },
                icon = { Icon(navigationItem.icon, contentDescription = navigationItem.label) },
                onClick = { onClick(navigationItem, index) }
            )
        }
    }
}
