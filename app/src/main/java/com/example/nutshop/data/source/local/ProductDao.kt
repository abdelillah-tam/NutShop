package com.example.nutshop.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nutshop.domain.Category
import com.example.nutshop.domain.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: Category) : Flow<List<Product?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(product: Product)

    @Insert
    fun addProducts(product: List<Product>)
}