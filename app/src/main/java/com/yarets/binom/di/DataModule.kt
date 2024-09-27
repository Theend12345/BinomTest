package com.yarets.binom.di

import com.yarets.binom.data.repository.AppRepositoryImpl
import com.yarets.binom.domain.AppRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideAppRepository(): AppRepository =
        AppRepositoryImpl()
}