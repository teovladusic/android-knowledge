package com.puzzle_agency.androidknowledge.knowledge.ui.animated_pager

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.puzzle_agency.androidknowledge.knowledge.ui.modifier.clickable.clickableWithBackground
import com.puzzle_agency.androidknowledge.knowledge.ui.view.image.StateAsyncImage
import com.puzzle_agency.androidknowledge.knowledge.util.extension.startOffsetForPage
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedPagerScreen() {
    var isTransformed by rememberSaveable { mutableStateOf(false) }
    val pagerState = rememberPagerState { CITY_IMAGES.size }

    val transformationSpec = TweenSpec<IntSize>(
        durationMillis = 500,
        easing = LinearOutSlowInEasing
    )

    val screenHeightDp = LocalConfiguration.current.screenHeightDp

    val sharedPagerElement = rememberContentWithOrbitalScope {
        CitiesPager(
            modifier = if (isTransformed) {
                Modifier.fillMaxSize()
            } else {
                Modifier
                    .fillMaxWidth()
                    .height((screenHeightDp / 2).dp)
            }
                .animateSharedElementTransition(this, transformSpec = transformationSpec)
                .zIndex(2f),
            cities = CITIES,
            pagerState = pagerState,
            transformed = isTransformed,
            onPageClick = { if (!isTransformed) isTransformed = true }
        )
    }

    Orbital {
        if (isTransformed) {
            SharedElementDetailScreen(
                sharedElement = sharedPagerElement,
                city = CITIES[pagerState.currentPage],
                onBack = { isTransformed = false }
            )
        } else {
            AnimatedPagerCitiesScreen(
                sharedPagerElement,
                CITIES[pagerState.currentPage]
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CitiesPager(
    modifier: Modifier,
    cities: List<City>,
    pagerState: PagerState,
    transformed: Boolean,
    onPageClick: () -> Unit
) {
    val pageSpacing by animateDpAsState(
        targetValue = if (transformed) 0.dp else 16.dp,
        label = "pageSpacingAnimation"
    )

    val contentPadding by animateDpAsState(
        targetValue = if (transformed) 0.dp else 24.dp,
        label = "contentPaddingAnimation"
    )

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = contentPadding),
        pageSpacing = pageSpacing,
        pageSize = PageSize.Fill,
        userScrollEnabled = !transformed
    ) { page ->
        LocationPage(
            city = cities[page],
            pagerState = pagerState,
            page = page,
            modifier = Modifier.fillMaxSize(),
            isTransformed = transformed,
            onPageClick = onPageClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LocationPage(
    modifier: Modifier,
    city: City,
    pagerState: PagerState,
    page: Int,
    isTransformed: Boolean,
    onPageClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .graphicsLayer {
                val startOffset = pagerState.startOffsetForPage(page)
                translationX = size.width * (startOffset * .99f)

                alpha = (2f - startOffset) / 2f
                val blur = (startOffset * 20f).coerceAtLeast(0.1f)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    renderEffect = RenderEffect
                        .createBlurEffect(blur, blur, Shader.TileMode.DECAL)
                        .asComposeRenderEffect()
                }

                val scale = 1f - (startOffset * .1f)
                scaleX = scale
                scaleY = scale
            }
            .clickableWithBackground(Color.Transparent, Color.Transparent) { onPageClick() },
        shape = RoundedCornerShape(if (isTransformed) 0.dp else 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.elevatedCardColors(),
    ) {
        StateAsyncImage(
            url = city.imageUrl,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

private val CITY_IMAGES = listOf(
    "https://mfiles.alphacoders.com/920/920131.jpg",
    "https://thesummermama.files.wordpress.com/2019/06/img_3045.jpg?w=720",
    "https://t4.ftcdn.net/jpg/02/57/75/51/360_F_257755130_JgTlcqTFxabsIKgIYLAhOFEFYmNgwyJ6.jpg",
    "https://img.freepik.com/free-photo/dubai-skyline-wallpaper_1409-6486.jpg",
    "https://images.pexels.com/photos/161772/las-vegas-nevada-cities-urban-161772.jpeg",
)

data class City(
    val imageUrl: String,
    val weatherText: String,
    val name: String,
    val description: String,
    val country: String
)

@Suppress("detekt.MaxLineLength")
private val CITIES = listOf(
    City(
        imageUrl = CITY_IMAGES[0],
        weatherText = "22 MOSTLY CLOUDY",
        name = "Paris",
        description = "A finely detailed miniature replica of the iconic Eiffel Tower, standing at 10 inches tall. Crafted from durable metal alloy, this statue captures the elegance and grandeur of the original monument, complete with intricate lattice work and a bronze finish.",
        country = "France"
    ),
    City(
        imageUrl = CITY_IMAGES[1],
        weatherText = "25 SUNNY",
        name = "Madrid",
        description = "Handcrafted flamenco guitar made from select Spanish woods, featuring intricate rosette detailing and a rich, resonant sound. This instrument embodies the soulful rhythms and passionate spirit of traditional Spanish music.",
        country = "Spain"
    ),
    City(
        imageUrl = CITY_IMAGES[2],
        weatherText = "18 PARTLY CLOUDY",
        name = "London",
        description = "A classic deerstalker hat reminiscent of the one famously worn by Sherlock Holmes. Made from high-quality wool tweed, this hat is perfect for sleuthing adventures or adding a touch of Victorian charm to any ensemble.",
        country = "England"
    ),
    City(
        imageUrl = CITY_IMAGES[3],
        weatherText = "30 CLEAR",
        name = "Dubai",
        description = "Exquisite 18-karat gold pendant inspired by traditional Arabic motifs, adorned with intricate filigree and studded with sparkling diamonds. This piece captures the opulence and sophistication of Dubai's vibrant cultural heritage.",
        country = "United Arab Emirates"
    ),
    City(
        imageUrl = CITY_IMAGES[4],
        weatherText = "28 MOSTLY SUNNY",
        name = "Las Vegas",
        description = "Authentic replica of a showgirl costume from the golden era of Las Vegas entertainment. Embellished with sequins, feathers, and rhinestones, this glamorous ensemble evokes the glitz and glamour of Sin City's legendary stage productions.",
        country = "USA"
    )
)
