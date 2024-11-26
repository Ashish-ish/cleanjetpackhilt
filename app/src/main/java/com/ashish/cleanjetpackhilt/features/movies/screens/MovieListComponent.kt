package com.ashish.cleanjetpackhilt.features.movies.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.aregyan.compose.R
import com.ashish.cleanjetpackhilt.data.model.Movies
import com.ashish.cleanjetpackhilt.features.movies.ui.MovieViewModel
import com.ashish.cleanjetpackhilt.utils.MovieNavigationItems
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun MovieListScreen(
    viewModel: MovieViewModel,
    navHostController: NavHostController
) {
    runCatching {
        val response = viewModel.movieResponse.value
        val keyboardController = LocalSoftwareKeyboardController.current

        if (response.data?.isNotEmpty() == true) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.padding(top=30.dp)
            ) {
                items(response.data.size) { index ->
                    val movie = response.data.getOrNull(index)?.results
                    movie?.let {
                        MovieList(results = movie) {
                            keyboardController?.hide()
                            viewModel.resetMovieDetail()
                            viewModel.getMoviesDetails(movie.imdbID)
                            navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                        }
                    }
                }
            }
        }

        if (response.error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = response.error)
            }
        }

        if (response.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if(response.data?.isEmpty() == true) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Please enter a keyword to search")
            }
        }

    }
}

@Composable
fun MovieList(results: Movies, onGettingClick: () -> Unit) {
    val screenWidth = LocalContext.current.resources.displayMetrics.widthPixels
    val imageWidth = (screenWidth / 3).dp

    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(2.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { onGettingClick() }
    ) {
        Column(modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .align(Alignment.Start)) {
            MoviePosterImage(results.Poster.orEmpty(),imageWidth)
            Column(modifier = Modifier.padding(top=5.dp)) {
                Text(text = results.Title ?: "", style = typography.labelSmall.copy(fontWeight = FontWeight.Bold), textAlign = TextAlign.Start,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(text = results.Year ?: "", style = typography.labelSmall.copy(color = Color.LightGray), textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Composable
fun MoviePosterImage(
    imageUrl: String,
    imageWidth: Dp
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        error = painterResource(id = R.drawable.error_image)
    )

    val isLoading = painter.state is AsyncImagePainter.State.Loading

    Box(modifier = Modifier
        .width(imageWidth)
        .aspectRatio(2f / 3f)
        .clip(RoundedCornerShape(8.dp))) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .width(imageWidth)
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp))
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer(
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1000),
                                repeatMode = RepeatMode.Restart
                            )
                        ),
                        color = Color.Gray,
                    )
            )
        }

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .width(imageWidth)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(16.dp))
        )
    }
}

