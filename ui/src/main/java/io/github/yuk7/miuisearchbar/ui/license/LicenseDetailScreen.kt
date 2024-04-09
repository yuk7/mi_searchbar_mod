package io.github.yuk7.miuisearchbar.ui.license

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.yuk7.miuisearchbar.ui.R

@Composable
fun LicenseDetailScreen(navHostController: NavHostController, title: String, body: String) {
    LicenseDetailScreenContent(
        onClickBack = navHostController::popBackStack,
        title = title,
        body = body
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseDetailScreenContent(onClickBack: () -> Unit, title: String, body: String) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = stringResource(id = R.string.common_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = body,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
fun LicenseDetailScreenPreview() {
    LicenseDetailScreenContent(
        onClickBack = {},
        title = "Title",
        body = "Body",
    )
}