package io.github.yuk7.miuisearchbar.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.yuk7.miuisearchbar.ui.extension.urlEncodedWithoutSpace
import io.github.yuk7.miuisearchbar.ui.license.LicenseDetailScreen
import io.github.yuk7.miuisearchbar.ui.license.LicenseListScreen
import io.github.yuk7.miuisearchbar.ui.top.TopScreen

enum class Screen {
    TOP,
    LICENSE_LIST,
    LICENSE_DETAIL,
}

fun NavController.navigateTopScreen() {
    navigate(Screen.TOP.name)
}

fun NavController.navigateToLicenseListScreen() {
    navigate(Screen.LICENSE_LIST.name)
}

fun NavController.navigateToLicenseDetailScreen(title: String, body: String) {
    navigate("${Screen.LICENSE_DETAIL.name}/${title.urlEncodedWithoutSpace}/${body.urlEncodedWithoutSpace}")
}


@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navHostController, startDestination = Screen.TOP.name) {
        composable(Screen.TOP.name) { TopScreen(navHostController, hiltViewModel()) }
        composable(Screen.LICENSE_LIST.name) { LicenseListScreen(navHostController) }
        composable(
            "${Screen.LICENSE_DETAIL.name}/{title}/{body}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("body") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            LicenseDetailScreen(
                navHostController,
                backStackEntry.arguments?.getString("title") ?: "",
                backStackEntry.arguments?.getString("body") ?: ""
            )
        }
    }
}