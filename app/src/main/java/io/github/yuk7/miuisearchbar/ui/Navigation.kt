package io.github.yuk7.miuisearchbar.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.yuk7.miuisearchbar.ui.top.TopScreen

enum class Screen {
    TOP
}

fun NavController.navigateTopScreen() {
    navigate(Screen.TOP.name)
}


@Composable
fun InitializeRoutes(navHostController: NavHostController) {
    NavHost(navHostController, startDestination = Screen.TOP.name) {
        composable(Screen.TOP.name) { TopScreen(navHostController) }
    }
}