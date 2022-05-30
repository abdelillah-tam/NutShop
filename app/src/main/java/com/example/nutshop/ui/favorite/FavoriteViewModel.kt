package com.example.nutshop.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutshop.data.source.CustomerRepository
import com.example.nutshop.domain.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {


    private val _state = MutableStateFlow(FavoriteUiState())
    val state = _state.asStateFlow()

    fun deleteFromFavorite(product : Product){
        viewModelScope.launch {
            customerRepository.deleteFromFavorite(product).collect{

            }
        }
    }

}