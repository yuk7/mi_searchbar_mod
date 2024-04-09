package io.github.yuk7.miuisearchbar.domain.usecase

import io.github.yuk7.miuisearchbar.domain.repository.AppPref
import io.github.yuk7.miuisearchbar.model.SearchBoxType
import javax.inject.Inject

class SetSearchBoxTypeUseCase @Inject constructor(
    private val appPref: AppPref
) {
    fun execute(searchBoxType: SearchBoxType) {
        appPref.searchBoxType = searchBoxType.typeName
    }
}