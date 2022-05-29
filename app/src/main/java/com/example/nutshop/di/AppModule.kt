package com.example.nutshop.di

import com.example.nutshop.data.source.*
import com.example.nutshop.data.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

annotation class ProductRemoteDataSource
annotation class ProductLocalDataSource
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun productRepository(
        dataSource: DataSource
    ) : ProductRepository {
        return ProductRepositoryImpl(dataSource)
    }

    @Provides
    fun customerRepository(dataSource: DataSource) : CustomerRepository{
        return CustomerRepositoryImpl(dataSource)
    }


    @Provides
    fun remoteProductDataSource() : DataSource {
        return RemoteDataSource
    }



}