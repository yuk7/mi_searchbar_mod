package io.github.yuk7.miuisearchbar.ui.license

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.util.withContext
import io.github.yuk7.miuisearchbar.ui.R
import io.github.yuk7.miuisearchbar.ui.navigateToLicenseDetailScreen
import io.github.yuk7.miuisearchbar.ui.theme.AppTheme

@Composable
fun LicenseListScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val libraries = remember { Libs.Builder().withContext(context).build().libraries }
    LicenseListScreenContent(
        libraries = libraries,
        onClickBack = navHostController::popBackStack,
        onClickLibrary = { title, body ->
            navHostController.navigateToLicenseDetailScreen(title, body)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseListScreenContent(
    libraries: List<Library>,
    onClickBack: () -> Unit,
    onClickLibrary: (title: String, body: String) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.license_oss_title))
                },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.common_back)
                        )
                    }
                },
                actions = {},
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 10.dp,
                bottom = paddingValues.calculateBottomPadding() + 10.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            items(libraries) { library ->
                ListItem(
                    modifier = Modifier.clickable {
                        onClickLibrary(
                            library.name,
                            "${library.name}\n" +
                                    "${library.licenses.firstOrNull()?.name ?: ""}\n" +
                                    library.developers.joinToString { (it.name ?: "") + "\n" } +
                                    "${library.website}\n" +
                                    "--------------------\n" +
                                    "${library.licenses.firstOrNull()?.licenseContent}"
                        )
                    },
                    headlineContent = { Text(text = library.name) },
                    trailingContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                            contentDescription = stringResource(id = R.string.common_next)
                        )
                    },
                )
            }
        }
    }
}

@Preview
@Composable
fun LicenseListScreenPreview() {
    AppTheme {
        LicenseListScreenContent(
            libraries = emptyList(),
            onClickBack = {},
            onClickLibrary = { _, _ -> }
        )
    }
}
