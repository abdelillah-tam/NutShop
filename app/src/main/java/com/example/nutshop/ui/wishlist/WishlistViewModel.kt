package com.example.nutshop.ui.wishlist

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
class WishlistViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {


    private val _state = MutableStateFlow(WishlistUiState())
    val state = _state.asStateFlow()

    fun deleteFromFavorite(product : Product){
        viewModelScope.launch {
            customerRepository.deleteFromFavorite(product).collect{

            }
        }
    }

    fun getProductsInFavorite(){
        viewModelScope.launch {
            customerRepository.getProdustsInFavorite().collect{ list ->
                _state.update {
                    it.copy(list = list)
                }
            }
        }
    }

}