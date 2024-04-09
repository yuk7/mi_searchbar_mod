package io.github.yuk7.miuisearchbar.ui.top

import android.util.Log
import androidx.lifecycle.ViewModel
import com.topjohnwu.superuser.Shell
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.yuk7.miuisearchbar.domain.usecase.GetAssistantTypeUseCase
import io.github.yuk7.miuisearchbar.domain.usecase.GetSearchBoxTypeUseCase
import io.github.yuk7.miuisearchbar.domain.usecase.SetAssistantTypeUseCase
import io.github.yuk7.miuisearchbar.domain.usecase.SetSearchBoxTypeUseCase
import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.Constants
import io.github.yuk7.miuisearchbar.model.SearchBoxType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class TopScreenViewModel @Inject constructor(
    private val getSearchBoxTypeUseCase: GetSearchBoxTypeUseCase,
    private val getAssistantTypeUseCase: GetAssistantTypeUseCase,
    private val setSearchBoxTypeUseCase: SetSearchBoxTypeUseCase,
    private val setAssistantTypeUseCase: SetAssistantTypeUseCase,
) : ViewModel() {
    private val listOfSearchBoxType = MutableStateFlow<List<SearchBoxType>?>(null)
    private val listOfAssistantType = MutableStateFlow<List<AssistantType>?>(null)
    private val currentSearchBoxType = MutableStateFlow<SearchBoxType?>(null)
    private val currentAssistantType = MutableStateFlow<AssistantType?>(null)
    private val error = MutableStateFlow<Throwable?>(null)

    val state = combine(
        listOfSearchBoxType,
        currentSearchBoxType,
        listOfAssistantType,
        currentAssistantType,
        error,
    ) { listOfSearchBoxType, currentSearchBoxType, listOfAssistantType, currentAssistantType, error ->
        if (error != null) {
            TopScreenState.Error(error)
        } else if (listOfSearchBoxType == null || currentSearchBoxType == null || listOfAssistantType == null || currentAssistantType == null) {
            TopScreenState.Loading
        } else {
            TopScreenState.Loaded(
                listOfSearchBoxType = listOfSearchBoxType,
                searchBoxType = currentSearchBoxType,
                listOfAssistantType = listOfAssistantType,
                assistantType = currentAssistantType,
            )
        }
    }

    init {
        listOfSearchBoxType.value = SearchBoxType.entries
        listOfAssistantType.value = AssistantType.entries
        runCatching {
            Pair(
                getSearchBoxTypeUseCase.execute(),
                getAssistantTypeUseCase.execute(),
            )
        }.onSuccess { (searchBoxType, assistantType) ->
            currentSearchBoxType.value = searchBoxType
            currentAssistantType.value = assistantType
        }.onFailure {
            error.value = it
            Log.e("TopScreenViewModel", "Failed to initial loading", it)
        }
    }

    fun setSearchBoxType(searchBoxType: SearchBoxType) {
        currentSearchBoxType.value = searchBoxType
        runCatching {
            setSearchBoxTypeUseCase.execute(searchBoxType)
        }.onFailure {
            Log.e("TopScreenViewModel", "Failed to set search box type", it)
        }
    }

    fun setAssistantType(assistantType: AssistantType) {
        currentAssistantType.value = assistantType
        runCatching {
            setAssistantTypeUseCase.execute(assistantType)
        }.onFailure {
            Log.e("TopScreenViewModel", "Failed to set assistant type", it)
        }
    }

    fun rebootHomeApp() {
        Shell.cmd("am force-stop ${Constants.MIUI_HOME_PACKAGE}").exec()
    }
}