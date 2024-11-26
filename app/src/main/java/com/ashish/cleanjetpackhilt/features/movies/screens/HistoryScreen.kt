package com.ashish.cleanjetpackhilt.features.movies.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.aregyan.compose.R
import com.ashish.cleanjetpackhilt.data.model.MovieDetail
import com.ashish.cleanjetpackhilt.features.movies.ui.MovieViewModel
import com.ashish.cleanjetpackhilt.utils.Constants.history
import com.ashish.cleanjetpackhilt.utils.Constants.notAvailable
import com.ashish.cleanjetpackhilt.utils.MovieNavigationItems

@Composable
fun RecentSearchHomeSearch(viewModel: MovieViewModel, navHostController: NavHostController) {
    val recentSearches = remember { mutableStateOf(viewModel.getRecentMovies().toList()) }

    RecentSearchScreen(
        recentSearches = recentSearches.value,
        onSearchClick = {
            navHostController.navigate(MovieNavigationItems.MovieSearch.createRoute(it))
        },
        onBackPress = {
            navHostController.popBackStack()
        },
        onClearClick = { movieDetail ->
            viewModel.removeRecentItemData(movieDetail)
            recentSearches.value = viewModel.getRecentMovies()
        }
    )
}

@Composable
fun RecentSearchScreen(
    recentSearches: List<MovieDetail>,
    onSearchClick: (String) -> Unit,
    onBackPress: () -> Unit,
    onClearClick: (MovieDetail) -> Unit
) {
    Scaffold(
        topBar = {
            HistoryTopBar(onBackPress)
        }
    ) { padding ->
        if (recentSearches.isEmpty()) {
            Text(
                text = notAvailable,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .wrapContentSize(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp)
            ) {
                items(recentSearches.size) { index ->
                    val search = recentSearches[index]
                    RecentSearchItem(
                        search = search,
                        onClick = onSearchClick,
                        onClearClick = onClearClick
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HistoryTopBar(onBackPress: () -> Unit) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { onBackPress() }
                )


                Text(
                    text = history,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 20.dp)
                )

            }
        }
    )
}

@Composable
fun RecentSearchItem(
    search: MovieDetail,
    onClick: (String) -> Unit,
    onClearClick: (MovieDetail) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = search.Title.orEmpty(),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            color = Color.Gray,
            modifier = Modifier
                .weight(1f)
                .clickable { onClick(search.Title.orEmpty()) }
                .padding(end = 8.dp)
        )

        Image(
            painter = rememberAsyncImagePainter(model = search.Poster,
                error = painterResource(id = R.drawable.error_image)),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(4.dp))
        )

        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Clear Icon",
            tint = Color.Gray,
            modifier = Modifier
                .padding(8.dp)
                .clickable { onClearClick(search) }
        )
    }
}