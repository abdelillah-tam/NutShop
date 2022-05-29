package com.example.nutshop.ui.home


import com.example.nutshop.domain.Category
import com.example.nutshop.domain.models.Product

data class HomeUiState(
    var category: Category? = Category.WHEYPROTEIN,
    var listOfProducts: List<Product?> = emptyList()
)
