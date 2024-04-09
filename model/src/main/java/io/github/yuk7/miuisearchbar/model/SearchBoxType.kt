package io.github.yuk7.miuisearchbar.model

enum class SearchBoxType(val typeName: String) {
    DEFAULT("default"),
    OS_DEFAULT("os_default"),
    GOOGLE("google"),
    ;
    companion object {
        fun fromTypeName(typeName: String): SearchBoxType {
            return entries.firstOrNull { it.typeName == typeName } ?: DEFAULT
        }
    }
}
