package com.dohyun.searchimgapp.di

import com.dohyun.searchimgapp.data.repository.SearchRepository
import com.dohyun.searchimgapp.data.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsSearchRepository(
        repositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}