package com.example.nutshop.ui.shopcart

import com.example.nutshop.domain.models.Product

data class ShopcartUiState(
    var list : List<Product?> = emptyList(),
    var total : Double = 0.00
)