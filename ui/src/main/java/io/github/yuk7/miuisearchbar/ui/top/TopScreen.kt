package io.github.yuk7.miuisearchbar.ui.top

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.SearchBoxType
import io.github.yuk7.miuisearchbar.ui.R
import io.github.yuk7.miuisearchbar.ui.extension.nameForUi
import io.github.yuk7.miuisearchbar.ui.navigateToLicenseListScreen
import io.github.yuk7.miuisearchbar.ui.theme.AppTheme

@Composable
fun TopScreen(navController: NavController, viewModel: TopScreenViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = TopScreenState.Loading)
    TopScreenContent(
        state = state,
        onClickInfo = navController::navigateToLicenseListScreen,
        onRestartHomeAppClicked = viewModel::rebootHomeApp,
        onSearchBoxSelected = viewModel::setSearchBoxType,
        onAssistantSelected = viewModel::setAssistantType,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopScreenContent(
    state: TopScreenState,
    onClickInfo: () -> Unit,
    onRestartHomeAppClicked: () -> Unit,
    onSearchBoxSelected: (searchBoxType: SearchBoxType) -> Unit,
    onAssistantSelected: (assistantType: AssistantType) -> Unit,
) {
    var restartDialogShown by remember { mutableStateOf(false) }
    if (restartDialogShown) {
        AlertDialog(
            onDismissRequest = {
                restartDialogShown = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRestartHomeAppClicked()
                        restartDialogShown = false
                    }
                ) {
                    Text(stringResource(id = R.string.common_ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        restartDialogShown = false
                    }
                ) {
                    Text(stringResource(id = R.string.common_cancel))
                }
            },
            title = {
                Text(stringResource(id = R.string.home_restart_dialog_title))
            },
            text = {
                Text(stringResource(id = R.string.home_restart_dialog_message))
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { restartDialogShown = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_restart_alt_24),
                            contentDescription = stringResource(id = R.string.restart_home_app)
                        )
                    }
                    IconButton(onClick = onClickInfo) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_info_outline_24),
                            contentDescription = stringResource(id = R.string.information)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (state) {
            is TopScreenState.Loading -> {

            }

            is TopScreenState.Error -> {
                TopScreenError(
                    paddingValues = paddingValues,
                    error = state.error,

                    )
            }

            is TopScreenState.Loaded -> {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = stringResource(id = R.string.app_details))
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = stringResource(id = R.string.pref_search_bar_engine),
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            MyDropdownMenuBox(
                                selectOptionTexts = state.listOfSearchBoxType.map {
                                    it.nameForUi
                                },
                                selectedOptionText = state.searchBoxType.nameForUi
                            ) {
                                onSearchBoxSelected(state.listOfSearchBoxType[it])
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = stringResource(id = R.string.pref_voice_assistant_engine),
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            MyDropdownMenuBox(
                                selectOptionTexts = state.listOfAssistantType.map {
                                    it.nameForUi

                                },
                                selectedOptionText = state.assistantType.nameForUi
                            ) {
                                onAssistantSelected(state.listOfAssistantType[it])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopScreenError(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    error: Throwable
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.error_message),
            fontSize = 18.sp
        )
        Text(text = error.localizedMessage ?: error.toString())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropdownMenuBox(
    selectOptionTexts: List<String>,
    selectedOptionText: String,
    onSelect: (index: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(stringResource(id = R.string.common_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            selectOptionTexts.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onSelect(index)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopScreen() {
    AppTheme {
        TopScreenContent(
            state = TopScreenState.Loaded(
                listOfSearchBoxType = emptyList(),
                searchBoxType = SearchBoxType.DEFAULT,
                listOfAssistantType = emptyList(),
                assistantType = AssistantType.DEFAULT,
            ),
            onClickInfo = {},
            onRestartHomeAppClicked = {},
            onSearchBoxSelected = {},
            onAssistantSelected = {},
        )
    }
}
