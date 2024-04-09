package io.github.yuk7.miuisearchbar.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_WORLD_READABLE
import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.Constants.KEY_ASSISTANT_TYPE
import io.github.yuk7.miuisearchbar.model.Constants.KEY_SEARCH_BOX_TYPE
import io.github.yuk7.miuisearchbar.model.Constants.SHARED_PREFS_NAME
import io.github.yuk7.miuisearchbar.model.SearchBoxType

interface AppPref {
    var searchBoxType: String
    var assistantType: String
}

class AppPrefImpl(context: Context) : AppPref {
    // In here, MAKE_WORLD_READABLE must be used to make the shared preferences readable by hooks working in other apps.
    @SuppressLint("WorldReadableFiles")
    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_WORLD_READABLE)
    override var searchBoxType: String
        get() = sharedPreferences.getString(KEY_SEARCH_BOX_TYPE, SearchBoxType.DEFAULT.typeName) ?: SearchBoxType.DEFAULT.typeName
        set(value) = sharedPreferences.edit().putString(KEY_SEARCH_BOX_TYPE, value).apply()

    override var assistantType: String
        get() = sharedPreferences.getString(KEY_ASSISTANT_TYPE, AssistantType.DEFAULT.typeName) ?: AssistantType.DEFAULT.typeName
        set(value) = sharedPreferences.edit().putString(KEY_ASSISTANT_TYPE, value).apply()
}

