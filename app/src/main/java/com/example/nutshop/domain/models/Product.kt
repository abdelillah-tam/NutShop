package com.example.nutshop.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nutshop.domain.Category
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val productId: String = UUID.randomUUID().toString(),
    val productName: String,
    val price: Double,
    var quantity: Int,
    val description: String,
    val productPictureLink: String,
    val category: Category,
    var quantityTaken: Int
) : Parcelable {

    constructor() : this("","",0.0,0,"","",Category.WHEYPROTEIN,0)

}
