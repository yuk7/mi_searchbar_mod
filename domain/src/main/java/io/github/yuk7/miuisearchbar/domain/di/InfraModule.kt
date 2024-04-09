package io.github.yuk7.miuisearchbar.domain.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.yuk7.miuisearchbar.domain.repository.AppPref
import io.github.yuk7.miuisearchbar.domain.repository.AppPrefImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object InfraModule {
    @Provides
    @Singleton
    fun provideAppPref(@ApplicationContext context: Context): AppPref = AppPrefImpl(context)
}