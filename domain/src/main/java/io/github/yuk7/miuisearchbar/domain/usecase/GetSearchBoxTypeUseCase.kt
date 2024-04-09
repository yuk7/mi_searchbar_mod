package io.github.yuk7.miuisearchbar.domain.usecase

import io.github.yuk7.miuisearchbar.domain.repository.AppPref
import io.github.yuk7.miuisearchbar.model.SearchBoxType
import javax.inject.Inject

class GetSearchBoxTypeUseCase @Inject constructor(
    private val appPref: AppPref
) {
    fun execute(): SearchBoxType = SearchBoxType.fromTypeName(appPref.searchBoxType)
}