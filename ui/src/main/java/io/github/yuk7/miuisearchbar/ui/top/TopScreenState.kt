package io.github.yuk7.miuisearchbar.ui.top

import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.SearchBoxType


sealed interface TopScreenState {
    data object Loading : TopScreenState
    data class Loaded(
        val listOfSearchBoxType: List<SearchBoxType>,
        val searchBoxType: SearchBoxType,
        val listOfAssistantType: List<AssistantType>,
        val assistantType: AssistantType,
    ) : TopScreenState
    data class Error(
        val error: Throwable,
    ) : TopScreenState
}