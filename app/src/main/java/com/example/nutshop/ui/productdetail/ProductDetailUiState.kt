package com.example.nutshop.ui.productdetail

import com.example.nutshop.domain.models.Product

data class ProductDetailUiState(
    var product: Product = Product(),
    var quantityChoosed: Int = 0,
    var nextQuantity: Int = 0,
    var addedToCart: Boolean = false,
    var firstRun: Boolean = true
)