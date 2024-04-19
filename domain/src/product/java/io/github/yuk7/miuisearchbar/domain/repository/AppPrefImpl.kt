package io.github.yuk7.miuisearchbar.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.Constants
import io.github.yuk7.miuisearchbar.model.SearchBoxType

class AppPrefImpl(private val context: Context) : AppPref {
    // In here, MAKE_WORLD_READABLE must be used to make the shared preferences readable by hooks working in other apps.
    private val sharedPreferences
        @Suppress("DEPRECATION")
        @SuppressLint("WorldReadableFiles")
        get() = context.getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            Context.MODE_WORLD_READABLE
        )
    override var searchBoxType: String
        get() = sharedPreferences.getString(Constants.KEY_SEARCH_BOX_TYPE, SearchBoxType.DEFAULT.typeName) ?: SearchBoxType.DEFAULT.typeName
        set(value) = sharedPreferences.edit().putString(Constants.KEY_SEARCH_BOX_TYPE, value).apply()

    override var assistantType: String
        get() = sharedPreferences.getString(Constants.KEY_ASSISTANT_TYPE, AssistantType.DEFAULT.typeName) ?: AssistantType.DEFAULT.typeName
        set(value) = sharedPreferences.edit().putString(Constants.KEY_ASSISTANT_TYPE, value).apply()
}
