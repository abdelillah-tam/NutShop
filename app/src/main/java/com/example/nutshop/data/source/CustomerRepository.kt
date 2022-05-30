package com.example.nutshop.data.source

import androidx.fragment.app.FragmentActivity
import com.example.nutshop.domain.models.Customer
import com.example.nutshop.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    fun login(email: String, password: String) : Flow<Boolean>
    fun signup(email: String, password: String) : Flow<Boolean>

    fun getProfileInformation() : Flow<Customer?>
    fun getProductsInShopcart() : Flow<List<Product?>>
    fun deleteProductFromShopcart(product: Product) : Flow<Boolean>

    fun addToFavorite(product: Product) : Flow<Boolean>
    fun deleteFromFavorite(product: Product) : Flow<Boolean>
}