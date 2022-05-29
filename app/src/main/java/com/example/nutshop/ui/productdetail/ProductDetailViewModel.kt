package com.example.nutshop.ui.productdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutshop.data.source.ProductRepository
import com.example.nutshop.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailUiState())
    val state = _state.asStateFlow()

    fun addToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            productRepository.addToCart(product, quantity).collect { result ->
                if (result) {
                    _state.update {
                        it.copy(

                            quantityChoosed = quantity,
                            addedToCart = result,
                            firstRun = false
                        )
                    }
                }
            }
        }
    }


    fun showProductOnScreen(product: Product?) {
        if (product != null) {
            _state.update {
                it.copy(
                    product = product
                )
            }
        }
    }
}