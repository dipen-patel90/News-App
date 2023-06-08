package com.ct.di

import com.ct.api.ApiInterface
import com.ct.repository.NewsRepository
import com.ct.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideNewsRepository(apiInterface: ApiInterface): NewsRepository =
        NewsRepositoryImpl(apiInterface)
}