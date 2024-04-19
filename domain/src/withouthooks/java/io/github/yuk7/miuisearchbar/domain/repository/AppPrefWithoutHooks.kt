package io.github.yuk7.miuisearchbar.domain.repository

import android.annotation.SuppressLint
import android.content.Context
import io.github.yuk7.miuisearchbar.model.AssistantType
import io.github.yuk7.miuisearchbar.model.Constants
import io.github.yuk7.miuisearchbar.model.SearchBoxType

class AppPrefWithoutHooks(private val context: Context) : AppPref {
    // In here, MAKE_PRIVATE must be used as usual.
    private val sharedPreferences
        @SuppressLint("WorldReadableFiles")
        get() = context.getSharedPreferences(
            Constants.SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )
    override var searchBoxType: String
        get() = sharedPreferences.getString(Constants.KEY_SEARCH_BOX_TYPE, SearchBoxType.DEFAULT.typeName) ?: SearchBoxType.DEFAULT.typeName
        set(value) = sharedPreferences.edit().putString(Constants.KEY_SEARCH_BOX_TYPE, value).apply()

    override var assistantType: String
        get() = sharedPreferences.getString(Constants.KEY_ASSISTANT_TYPE, AssistantType.DEFAULT.typeName) ?: AssistantType.DEFAULT.typeName
        set(value) = sharedPreferences.edit().putString(Constants.KEY_ASSISTANT_TYPE, value).apply()
}
