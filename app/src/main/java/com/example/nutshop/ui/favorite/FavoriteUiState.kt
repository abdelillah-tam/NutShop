package com.example.nutshop.ui.favorite

import com.example.nutshop.domain.models.Product

data class FavoriteUiState(
    var list: List<Product?> = emptyList()
)