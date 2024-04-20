package io.github.yuk7.miuisearchbar.hook.utils

import de.robv.android.xposed.XSharedPreferences
import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.Constants
import io.github.yuk7.miuisearchbar.model.SearchBoxType

class PreferenceUtils {
    private val pref by lazy {
        XSharedPreferences(Constants.APP_PACKAGE_NAME, Constants.SHARED_PREFS_NAME)
    }

    fun getAssistantType() = AssistantType.fromTypeName(
        pref.getString(
            Constants.KEY_ASSISTANT_TYPE,
            null
        ) ?: AssistantType.DEFAULT.typeName
    )

    fun getSearchBoxType() = SearchBoxType.fromTypeName(
        pref.getString(
            Constants.KEY_SEARCH_BOX_TYPE,
            null
        ) ?: SearchBoxType.DEFAULT.typeName
    )
}