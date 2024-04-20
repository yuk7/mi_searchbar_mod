package io.github.yuk7.miuisearchbar.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.yuk7.miuisearchbar.BuildConfig
import io.github.yuk7.miuisearchbar.core.annotations.VersionName
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @VersionName
    fun providesVersionName(): String = BuildConfig.VERSION_NAME
}