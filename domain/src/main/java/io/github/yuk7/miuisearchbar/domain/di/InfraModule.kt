package io.github.yuk7.miuisearchbar.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.yuk7.miuisearchbar.domain.repository.AppPref
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InfraModule {
    @Provides
    @Singleton
    fun provideAppPref(): AppPref = AppPref()
}