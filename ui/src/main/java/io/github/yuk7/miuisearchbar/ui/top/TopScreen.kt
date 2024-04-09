package io.github.yuk7.miuisearchbar.ui.top

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.github.yuk7.miuisearchbar.ui.theme.AppTheme

@Composable
fun TopScreen(navController: NavController, viewModel: TopScreenViewModel) {
    TopScreenContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScreenContent() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hello")
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Text(
                text = "Hello"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopScreen() {
    AppTheme {
        TopScreenContent()
    }
}
