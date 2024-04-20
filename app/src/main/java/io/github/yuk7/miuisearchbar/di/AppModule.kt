package io.github.yuk7.miuisearchbar.di

import androidx.annotation.Keep
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.yuk7.miuisearchbar.BuildConfig
import io.github.yuk7.miuisearchbar.MainApplication
import io.github.yuk7.miuisearchbar.core.annotations.FrameworkVersion
import io.github.yuk7.miuisearchbar.core.annotations.VersionName
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @VersionName
    fun providesVersionName(): String = BuildConfig.VERSION_NAME

    @Singleton
    @Provides
    @FrameworkVersion
    @Keep
    fun providesFrameworkVersion(): Int? = MainApplication.instance.getFrameworkVersion()
}