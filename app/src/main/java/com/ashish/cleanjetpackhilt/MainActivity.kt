package com.ashish.cleanjetpackhilt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ashish.cleanjetpackhilt.features.movies.ui.MovieViewModel
import com.ashish.cleanjetpackhilt.features.navigation.MovieNavigation
import com.ashish.cleanjetpackhilt.ui.theme.MovieAppTheme
import com.ashish.cleanjetpackhilt.utils.ConnectivityObserver
import com.ashish.cleanjetpackhilt.utils.Constants.internetConnectError
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = ConnectivityObserver(this)

        setContent {
            MovieAppTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(color = Color.Black)

                var isConnected by remember { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    connectivityObserver.isConnected.collectLatest { connectionStatus ->
                        isConnected = connectionStatus
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        if (!isConnected) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Red)
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = internetConnectError,
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        MovieNavigation(viewModel)
                    }
                }
            }
        }
    }

}