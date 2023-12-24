package com.puzzle_agency.androidknowledge.knowledge.ui.state.revolut_clone.view.header

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.puzzle_agency.androidknowledge.R
import com.puzzle_agency.androidknowledge.knowledge.ui.view.search.CustomSearchBar
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily

object SampleHeaderView {

    private val iconsBackgroundColor = Color(color = 0xFF647165).copy(alpha = 0.7f)

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Compose(headerState: HeaderState) {
        val searchActive = headerState.uiState.isSearchActive

        Row(
            modifier = Modifier
                .background(headerState.backgroundColor)
                .then(if (searchActive) Modifier else Modifier.statusBarsPadding())
                .padding(
                    horizontal = if (searchActive) 0.dp else 16.dp,
                    vertical = if (searchActive) 0.dp else 6.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (searchActive) 0.dp else 8.dp)
        ) {
            AnimatedVisibility(visible = !searchActive) {
                UserInitialsView(
                    initials = headerState.uiState.userInitials,
                    onClick = headerState::onUserInitialsClick
                )
            }

            CustomSearchBar(
                query = headerState.uiState.searchQuery,
                onQueryChange = headerState::queryChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp), // add bottom padding to match search bar top padding
                active = searchActive,
                onActiveChange = headerState::onSearchActiveChange,
                colors = SearchBarDefaults.colors(
                    containerColor = iconsBackgroundColor.copy(
                        alpha = if (searchActive) 1f else iconsBackgroundColor.alpha
                    )
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search),
                        color = Color.White,
                        fontFamily = NunitoFontFamily
                    )
                },
                trailingIcon = {
                    SearchBarTrailingIcon(
                        searchActive = searchActive,
                        onStatsClick = headerState::onStatsClick,
                        onCardsClick = headerState::onCardsClick,
                        query = headerState.uiState.searchQuery,
                        onQueryChange = headerState::queryChanged
                    )
                },
                content = {
                    if (headerState.uiState.searchResults.isNotEmpty()) {
                        SearchResults(results = headerState.uiState.searchResults)
                    }
                }
            )
        }
    }

    @Composable
    private fun SearchResults(results: List<String>) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(results) { result ->
                Text(
                    text = result,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }

    @Composable
    fun SearchBarTrailingIcon(
        searchActive: Boolean,
        onStatsClick: () -> Unit,
        onCardsClick: () -> Unit,
        query: String,
        onQueryChange: (String) -> Unit
    ) {
        AnimatedVisibility(
            visible = !searchActive,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            ToolbarIconButtons(
                onStatsClick = onStatsClick,
                onCardsClick = onCardsClick
            )
        }

        AnimatedVisibility(
            visible = searchActive && query.isNotEmpty(),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            ToolbarIconButton(imageVector = Icons.Default.Close) { onQueryChange("") }
        }
    }

    @Composable
    private fun UserInitialsView(initials: String, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color = Color(color = 0XFFFF40B0))
                .clickable { onClick() }
        ) {
            Text(
                text = initials,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontFamily = NunitoFontFamily
            )
        }
    }

    @Composable
    fun ToolbarIconButtons(onStatsClick: () -> Unit, onCardsClick: () -> Unit) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ToolbarIconButton(imageVector = Icons.Default.BarChart) { onStatsClick() }
            ToolbarIconButton(imageVector = Icons.Default.CreditCard) { onCardsClick() }
        }
    }

    @Composable
    private fun ToolbarIconButton(imageVector: ImageVector, onClick: () -> Unit) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
