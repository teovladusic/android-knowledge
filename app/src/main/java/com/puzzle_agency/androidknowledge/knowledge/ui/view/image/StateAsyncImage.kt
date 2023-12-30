package com.puzzle_agency.androidknowledge.knowledge.ui.view.image

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun StateAsyncImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    shape: Shape = RectangleShape,
    contentScale: ContentScale = ContentScale.Crop
) {
    SubcomposeAsyncImage(
        modifier = modifier.clip(shape),
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Error",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(text = "Loading...", modifier = Modifier.align(Alignment.Center))
                }
            }

            else -> SubcomposeAsyncImageContent()
        }
    }
}
