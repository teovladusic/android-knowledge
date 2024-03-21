package com.puzzle_agency.androidknowledge.knowledge.ui.animated_pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Rocket
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.puzzle_agency.androidknowledge.knowledge.ui.modifier.clickable.clickableWithBounce
import com.puzzle_agency.androidknowledge.knowledge.ui.view.image.StateAsyncImage
import com.puzzle_agency.androidknowledge.ui.theme.NunitoFontFamily
import com.skydoves.orbital.OrbitalScope

@Composable
fun OrbitalScope.AnimatedPagerCitiesScreen(
    sharedElement: @Composable OrbitalScope.() -> Unit,
    city: City
) {
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 6.dp),
        topBar = {
            Toolbar(onProfileClick = {})
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Subtitle()

            Spacer(modifier = Modifier.height(16.dp))

            sharedElement()

            CityInfo(city = city)

            Spacer(modifier = Modifier.height(24.dp))

            AdventuresSection()
        }
    }
}

@Composable
private fun AdventuresSection() {
    Column(modifier = Modifier.padding(horizontal = 32.dp)) {
        Text(
            text = "Feeling Adventurous?",
            fontSize = 26.sp,
            fontFamily = NunitoFontFamily,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            letterSpacing = -(0.5).sp
        )

        Text(
            text = "Get inspiration from trending categories",
            fontSize = 12.sp,
            fontFamily = NunitoFontFamily,
            color = Color.DarkGray,
            lineHeight = 12.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AdventureCategoryItem(ADVENTURES[0])
            AdventureCategoryItem(ADVENTURES[1])
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AdventureCategoryItem(ADVENTURES[2])
            AdventureCategoryItem(ADVENTURES[3])
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun RowScope.AdventureCategoryItem(adventureCategory: AdventureCategory) {
    Box(
        modifier = Modifier
            .height(84.dp)
            .weight(1f)
            .clickableWithBounce {}
            .clip(RoundedCornerShape(8.dp))
    ) {
        StateAsyncImage(url = adventureCategory.imageUrl, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Text(
            text = adventureCategory.name,
            color = Color.White,
            fontFamily = NunitoFontFamily,
            fontSize = 17.sp,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun CityInfo(city: City) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = city.name,
                fontSize = 24.sp,
                fontFamily = NunitoFontFamily,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = city.country.uppercase(),
                fontSize = 12.sp,
                fontFamily = NunitoFontFamily,
                color = Color.Gray,
                lineHeight = 12.sp,
            )
        }

        FilledTonalButton(
            onClick = {},
            colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFF5822BB)),
            shape = RoundedCornerShape(6.dp),
            contentPadding = PaddingValues(6.dp),
            modifier = Modifier.height(34.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Rocket,
                contentDescription = "rocket",
                tint = Color.White,
                modifier = Modifier
                    .size(22.dp)
                    .rotate(45f)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "0.4 BTC",
                lineHeight = 14.sp,
                fontFamily = NunitoFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun Subtitle() {
    Text(
        text = "Top travel locations today",
        fontSize = 14.sp,
        modifier = Modifier.padding(horizontal = 24.dp),
        color = Color.Black,
        fontFamily = NunitoFontFamily,
        lineHeight = 14.sp
    )
}

@Suppress("detekt.MaximumLineLength")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(onProfileClick: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 8.dp),
        title = {
            Text(
                text = "Explore",
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                letterSpacing = (-0.8).sp,
                fontFamily = NunitoFontFamily,
            )
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                AsyncImage(
                    model = "https://raw.githubusercontent.com/Ashwinvalento/cartoon-avatar/master/lib/images/female/10.png",
                    contentDescription = "user1",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}

data class AdventureCategory(
    val imageUrl: String,
    val name: String
)

@Suppress("detekt.MaxLineLength")
private val ADVENTURES = listOf(
    AdventureCategory(
        imageUrl = "https://img.freepik.com/free-photo/wide-angle-shot-single-tree-growing-clouded-sky-during-sunset-surrounded-by-grass_181624-22807.jpg",
        name = "Nature"
    ),
    AdventureCategory(
        imageUrl = "https://cdn.pixabay.com/photo/2017/07/21/23/57/concert-2527495_1280.jpg",
        name = "Party"
    ),
    AdventureCategory(
        imageUrl = "https://img.freepik.com/premium-photo/futuristic-travel-speed-light-time-travel-with-super-fast-speed_950002-61340.jpg",
        name = "Futuristic"
    ),
    AdventureCategory(
        imageUrl = "https://artchitectourstravel.com/wp-content/uploads/2016/12/brazil-architecture.jpg",
        name = "Architecture"
    ),
)
