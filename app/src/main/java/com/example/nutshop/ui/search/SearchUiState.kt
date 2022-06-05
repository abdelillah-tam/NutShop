package com.example.nutshop.ui.search

import com.example.nutshop.domain.models.Product

data class SearchUiState(
    var list : List<Product?> = emptyList()
)