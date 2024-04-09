package io.github.yuk7.miuisearchbar.model

enum class AssistantType(val typeName: String) {
    DEFAULT("default"),
    OS_DEFAULT("os_default"),
    GOOGLE("google"),
    GOOGLE_VOICE_SEARCH("google_voice_search"),
    ;
    companion object {
        fun fromTypeName(typeName: String): AssistantType {
            return entries.firstOrNull { it.typeName == typeName } ?: DEFAULT
        }
    }
}
