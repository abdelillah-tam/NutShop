package com.example.nutshop.di

import com.example.nutshop.data.source.FakeProductRepository
import com.example.nutshop.data.source.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    fun productRepository() : ProductRepository {
        return FakeProductRepository()
    }
}