package com.example.nutshop.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nutshop.domain.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class NutShopDatabase : RoomDatabase(){

    abstract fun productDao(): ProductDao

}