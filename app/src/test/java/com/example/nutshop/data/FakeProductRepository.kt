package com.example.nutshop.data

import com.example.nutshop.data.source.ProductRepository
import com.example.nutshop.domain.Category
import com.example.nutshop.domain.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductRepository : ProductRepository {

    val list = mutableListOf<Product>(
        Product(
            productName = "OPTIMUM NUTRITION GOLD STANDARD 100% WHEY PROTEIN",
            price = 39.99,
            quantity = 33,
            description = "Each serving of the world’s best-selling whey protein powder provides 24 grams of high-quality whey protein primarily from Whey Protein Isolate, which has had excess carbohydrates, fat, and lactose ‘isolated’ out using sophisticated filtering technologies.",
            productPictureLink = "",
            category = Category.WHEYPROTEIN
        ),
        Product(
            productName ="JYM SUPPLEMENT SCIENCE PRO JYM PROTEIN POWDER",
            price = 39.99,
            quantity = 58,
            description = "Pro JYM is a premium protein powder, an ideal blend of the three most effective types of proteins for building muscle: whey, micellar casein, and egg protein. In synergy with Pre JYM and Post JYM, Pro JYM is your go-to protein both before and after intense workouts.",
            productPictureLink = "",
            category = Category.WHEYPROTEIN
        ),
        Product(
            productName = "EVLUTION NUTRITION ENGN SHRED PRE WORKOUT",
            price = 39.99,
            quantity = 2258,
            description = "ENGN SHRED’s revolutionary Fat Burning Pre-Workout formula is engineered to be the most complete, fully dosed thermogenic fuel that burns fat and boosts metabolism while accelerating your performance, energy, power and focus.",
            productPictureLink = "",
            category = Category.PREWORKOUT
        ),
        Product(
            productName = "CELLUCOR C4 ORIGINAL PRE WORKOUT",
            price = 29.99,
            quantity = 348,
            description = "C4 Original is fueled by classic energy, endurance, pumps, and performance. Whether you’re just starting out or striving to reach that next level, C4 Original will help you unlock your full potential.",
            productPictureLink = "",
            category = Category.PREWORKOUT
        )
    )
    val shopCart = mutableListOf<Product>()


    override fun getProductsByCategory(category: Category): Flow<List<Product>> = flow {
        emit(list.filter {
            it.category.equals(category)
        })
    }

    override fun addToCart(product: Product, quantity: Int): Boolean {
        if (list.contains(product)){
            return calculateQuantity(product, quantity)
        }
        return false
    }


    fun calculateQuantity(product: Product, quantity: Int) : Boolean{
        if (quantity <= product.quantity) {
            val addedProduct = product.copy(quantity = quantity)
            shopCart += addedProduct
            product.quantity -= quantity
            list[list.indexOf(product)] = product
            return true
        }
        return false
    }
}