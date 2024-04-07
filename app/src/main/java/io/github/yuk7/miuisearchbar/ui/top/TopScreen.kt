package io.github.yuk7.miuisearchbar.ui.top

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.github.yuk7.miuisearchbar.ui.theme.AppTheme

@Composable
fun TopScreen(navController: NavController, viewModel: TopScreenViewModel) {
    TopScreenContent()
}

@Composable
fun TopScreenContent() {
    Text(
        text = "Hello"
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopScreen() {
    AppTheme {
        TopScreenContent()
    }
}
