package io.github.yuk7.miuisearchbar.domain.usecase

import io.github.yuk7.miuisearchbar.domain.repository.AppPref
import io.github.yuk7.miuisearchbar.model.AssistantType
import javax.inject.Inject

class SetAssistantTypeUseCase @Inject constructor(
    private val appPref: AppPref
) {
    fun execute(assistantType: AssistantType) {
        appPref.assistantType = assistantType.typeName
    }
}