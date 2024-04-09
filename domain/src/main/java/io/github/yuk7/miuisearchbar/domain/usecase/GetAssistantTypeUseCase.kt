package io.github.yuk7.miuisearchbar.domain.usecase

import io.github.yuk7.miuisearchbar.domain.repository.AppPref
import io.github.yuk7.miuisearchbar.model.AssistantType
import javax.inject.Inject

class GetAssistantTypeUseCase @Inject constructor(
    private val appPref: AppPref
) {
    fun execute(): AssistantType = AssistantType.fromTypeName(appPref.assistantType)
}