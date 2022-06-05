package com.example.nutshop.data.source

import com.example.nutshop.domain.Category
import com.example.nutshop.domain.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val dataSource: DataSource
) : ProductRepository {


    override fun getProductsByCategory(category: Category): Flow<List<Product?>> = flow {
        dataSource.getProductsByCategory(category).collect{
            emit(it)
        }
    }


    override fun addToCart(product: Product, quantityYouWant: Int): Flow<Boolean> = flow {
        dataSource.addToCart(product, quantityYouWant).collect{
            emit(it)
        }
    }

    override fun searchForProducts(word: String): Flow<List<Product?>> = flow{
        dataSource.searchForProducts(word).collect{
            emit(it)
        }
    }
}