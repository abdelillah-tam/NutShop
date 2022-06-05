package com.example.nutshop.data.source

import com.example.nutshop.domain.Category
import com.example.nutshop.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {


    fun getProductsByCategory(category: Category) : Flow<List<Product?>>
    fun addToCart(product: Product, quantity: Int) : Flow<Boolean>
    fun searchForProducts(word: String) : Flow<List<Product?>>
}