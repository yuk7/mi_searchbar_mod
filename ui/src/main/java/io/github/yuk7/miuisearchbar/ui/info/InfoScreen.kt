package io.github.yuk7.miuisearchbar.ui.info

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import io.github.yuk7.miuisearchbar.ui.R
import io.github.yuk7.miuisearchbar.ui.navigateToLicenseListScreen
import io.github.yuk7.miuisearchbar.ui.theme.AppTheme

@Composable
fun InfoScreen(navHostController: NavHostController, viewModel: InfoScreenViewModel) {
    val context = LocalContext.current
    val githubUrl = stringResource(id = R.string.github_url)
    InfoScreenContent(
        state = viewModel.state,
        onClickBack = navHostController::popBackStack,
        onClickGitHub = {
            context.startActivity(Intent(ACTION_VIEW, Uri.parse(githubUrl)))
        },
        onClickLicenses = navHostController::navigateToLicenseListScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreenContent(
    state: InfoScreenState,
    onClickBack: () -> Unit,
    onClickGitHub: () -> Unit,
    onClickLicenses: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.information))
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
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(vertical = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 35.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 20.sp
                    )
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = state.versionName,
                        color = Color.Gray,
                    )
                }
                HorizontalDivider(Modifier.padding(vertical = 10.dp))
                InfoScreenCardItem(
                    title = stringResource(id = R.string.version),
                    detail = state.versionName,
                )
                InfoScreenCardItem(
                    title = stringResource(id = R.string.author),
                    detail = stringResource(id = R.string.author_name)
                )
                InfoScreenCardItem(
                    title = stringResource(id = R.string.module_working_status),
                    detail = stringResource(
                        id = if (state.frameworkVersion != null)
                            R.string.module_is_enabled
                        else
                            R.string.module_is_failed
                    )
                )
                if (state.frameworkVersion != null) {
                    InfoScreenCardItem(
                        title = stringResource(id = R.string.framework_api_version),
                        detail = state.frameworkVersion.toString()
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                )
            }
            ListItem(
                modifier = Modifier.clickable(onClick = onClickGitHub),
                headlineContent = { Text(text = stringResource(R.string.github)) },
                trailingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = stringResource(id = R.string.common_next)
                    )
                }
            )
            ListItem(
                modifier = Modifier.clickable(onClick = onClickLicenses),
                headlineContent = { Text(text = stringResource(R.string.license_oss_title)) },
                trailingContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_navigate_next_24),
                        contentDescription = stringResource(id = R.string.common_next)
                    )
                }
            )
        }
    }
}

@Composable
fun InfoScreenCardItem(
    modifier: Modifier = Modifier,
    title: String,
    detail: String? = null,
) {
    OutlinedCard(modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp
            )
            detail?.let {
                Text(
                    text = it,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInfoScreen() {
    AppTheme {
        InfoScreenContent(
            state = InfoScreenState("versionName", null),
            onClickBack = {},
            onClickGitHub = {},
            onClickLicenses = {},
        )
    }
}
