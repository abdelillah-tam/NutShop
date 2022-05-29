package com.example.nutshop.ui.shopcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutshop.data.source.CustomerRepository
import com.example.nutshop.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopcartViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {


    private val _state = MutableStateFlow(ShopcartUiState())
    val state = _state.asStateFlow()


    fun getProductsFromShopcart(){
        viewModelScope.launch {
            customerRepository.getProductsInShopcart().collect { list ->
                _state.update {
                    it.copy(list = list)
                }
            }
        }
    }


    fun deleteProductFromShopcart(product: Product){
        viewModelScope.launch {
            customerRepository.deleteProductFromShopcart(product).collect{

            }
        }
    }
}