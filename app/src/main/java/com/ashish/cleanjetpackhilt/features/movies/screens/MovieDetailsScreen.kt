package com.ashish.cleanjetpackhilt.features.movies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.aregyan.compose.R
import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import com.ashish.cleanjetpackhilt.features.movies.ui.MovieViewModel
import kotlinx.coroutines.delay

@Composable
fun MovieDetailScreen(
    viewModel: MovieViewModel,
    navController: NavController
) {
    val response = viewModel.movieDetails.value.data
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageHeight = screenHeight * 0.5f
    val isNavigating = remember { mutableStateOf(false) }
    LaunchedEffect(isNavigating.value) {
        if (isNavigating.value) {
            delay(300) // Delay to prevent multiple rapid clicks
            isNavigating.value = false
        }
    }
    Scaffold(
        content = { padding ->

            response?.let { movie ->
                Box(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    MovieDetailImageComponent(movie, imageHeight)

                    IconButton(
                        onClick = {
                            if (!isNavigating.value) {
                                isNavigating.value = true
                                navController.popBackStack()
                            }
                        },
                                modifier = Modifier
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    val modifier = Modifier
                        .fillMaxSize()
                        .padding(top = (imageHeight * 4 / 5), start = 30.dp, end = 30.dp)
                        .align(Alignment.BottomCenter)
                        .clipToBounds()
                    MovieDetailCardComponent(movie, modifier)
                }
            } ?: run {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}

@Composable
private fun MovieDetailCardComponent(
    movie: MovieDetail,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.8f)),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = modifier
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.White.copy(alpha = 0.3f), Color.Transparent),
                            startY = 0f,
                            endY = 300f
                        )
                    )
            )


            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = movie.Title ?: "Title not available",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.imdbRating ?: "N/A",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Runtime",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = movie.Runtime ?: "N/A",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                MovieMetaDataComponent(movie)

                Spacer(modifier = Modifier.height(12.dp))

                HorizontalDivider(thickness = 0.5.dp, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = movie.Plot ?: "Description not available",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Director: ${movie.Director}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Writer: ${movie.Writer}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Actors: ${movie.Actors}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
private fun MovieMetaDataComponent(movie: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.Type?.replaceFirstChar { it.uppercase() } ?: "N/A",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .weight(1f, fill = false)
                .wrapContentWidth(Alignment.End)
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            movie.Genre?.split(",")?.forEach { genre ->
                Text(
                    text = genre.trim(),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun MovieDetailImageComponent(
    movie: MovieDetail,
    imageHeight: Dp
) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(
                model = movie.Poster,
                error = painterResource(id = R.drawable.error_image)
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = 300f
                    )
                )
        )
    }
}