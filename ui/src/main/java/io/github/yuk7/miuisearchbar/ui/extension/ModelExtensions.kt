package io.github.yuk7.miuisearchbar.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.SearchBoxType
import io.github.yuk7.miuisearchbar.ui.R

val SearchBoxType.nameForUi
    @Composable get() = when (this) {
        SearchBoxType.DEFAULT -> stringResource(R.string.search_box_engine_default_name)
        SearchBoxType.OS_DEFAULT -> stringResource(R.string.search_box_engine_os_default_name)
        SearchBoxType.GOOGLE -> stringResource(R.string.search_box_engine_google_name)
    }

val AssistantType.nameForUi
    @Composable get() = when (this) {
        AssistantType.DEFAULT -> stringResource(R.string.voice_assistant_engine_default_name)
        AssistantType.OS_DEFAULT -> stringResource(R.string.voice_assistant_engine_os_default_name)
        AssistantType.GOOGLE -> stringResource(R.string.voice_assistant_engine_google_name)
        AssistantType.GOOGLE_VOICE_SEARCH -> stringResource(R.string.voice_assistant_engine_google_voice_search_name)
    }