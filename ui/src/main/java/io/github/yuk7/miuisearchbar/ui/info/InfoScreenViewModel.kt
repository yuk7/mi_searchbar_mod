package io.github.yuk7.miuisearchbar.ui.info

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.yuk7.miuisearchbar.core.annotations.FrameworkVersion
import io.github.yuk7.miuisearchbar.core.annotations.VersionName
import javax.inject.Inject

@HiltViewModel
class InfoScreenViewModel @Inject constructor(
    @VersionName private val versionName: String,
    @FrameworkVersion private val frameworkVersion: Int?,
) : ViewModel() {
    val state = InfoScreenState(versionName, frameworkVersion)
}