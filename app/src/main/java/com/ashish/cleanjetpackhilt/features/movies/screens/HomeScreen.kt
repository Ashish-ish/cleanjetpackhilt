package com.ashish.cleanjetpackhilt.features.movies.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ashish.cleanjetpackhilt.features.movies.ui.MovieViewModel
import com.ashish.cleanjetpackhilt.utils.MovieNavigationItems
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseScreen(viewModel: MovieViewModel, navHostController: NavHostController, defaultText: String="") {
    val searchText by remember { mutableStateOf(TextFieldValue(defaultText)) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp, bottom = 10.dp, end = 10.dp, start = 10.dp)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f).align(Alignment.CenterVertically)
                            ) {
                                Text(
                                    text = "Browse",
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "Movies",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = Color.Gray,
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Create Icon",
                                tint = Color.White,
                                modifier = Modifier.padding(end = 15.dp).clickable {
                                    navHostController.navigate(MovieNavigationItems.RecentSearch.route)
                                }
                            )
                        }
                    }
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    DebouncedSearchBar(searchText, viewModel)
                }
                MovieListScreen(viewModel, navHostController)
            }
        }

    }
}

@OptIn(FlowPreview::class)
@Composable
fun DebouncedSearchBar(
    searchText: TextFieldValue,
    viewModel: MovieViewModel
) {
    var textState by remember { mutableStateOf(searchText) }
    var lastSearchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        snapshotFlow { textState.text }
            .debounce(300) // Debounce to wait for 300ms after the last text change
            .distinctUntilChanged() // Only trigger when the text changes
            .collect { newText ->
                if (newText != lastSearchText) {
                    lastSearchText = newText
                    viewModel.searchMovies(newText)
                }
            }
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = textState,
        onValueChange = { newText ->
            textState = newText
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                viewModel.searchMovies(textState.text)
            }
        ),
        singleLine = true,
        textStyle = TextStyle(color = Color.White),
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray, RoundedCornerShape(8.dp)).
                padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Box(Modifier.weight(1f)) {
                        if (textState.text.isEmpty()) {
                            Text("Search movies", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            }
        }
    )
}
