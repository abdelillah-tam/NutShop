package com.example.nutshop.data.source

import com.example.nutshop.domain.Category
import com.example.nutshop.domain.models.Product
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface DataSource {

    fun getProductsByCategory(category: Category) : Flow<List<Product?>>
    fun addToCart(product: Product, quantityYouWant: Int) : Flow<Boolean>
    fun login(email: String, password: String) : Flow<Boolean>
    fun signup(email: String, password: String) : Flow<Boolean>
    fun getProfileInformation() : Flow<DocumentSnapshot>
    fun getProductsInShopcart() : Flow<List<Product?>>
    fun deleteProductFromShopcart(product: Product) : Flow<Boolean>
}