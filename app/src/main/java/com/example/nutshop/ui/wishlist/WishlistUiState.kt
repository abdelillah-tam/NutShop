package com.example.nutshop.ui.wishlist

import com.example.nutshop.domain.models.Product

data class WishlistUiState(
    var list: List<Product?> = emptyList()
)