package com.example.nutshop.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutshop.data.source.ProductRepository
import com.example.nutshop.domain.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {


    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()


    fun getProductsByCategory(category: Category) {
        viewModelScope.launch {
            productRepository.getProductsByCategory(category).collect { list ->
                _state.update {
                    it.copy(listOfProducts = list)
                }
            }
        }
    }

    fun chooseCategory(category: Category){
        viewModelScope.launch {
            _state.update {
                it.copy(category = category)
            }
            getProductsByCategory(_state.value.category!!)
        }
    }

}