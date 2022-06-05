package com.example.nutshop.ui.search

import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutshop.data.source.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {


    private val _state = MutableStateFlow(SearchUiState())
    val state = _state.asStateFlow()

    fun searchForProducts(word: String){
        viewModelScope.launch {
            productRepository.searchForProducts(word.uppercase(Locale.getDefault())).collect{ list ->
                _state.update {
                    it.copy(list = list)
                }
            }
        }
    }


    fun clearList(){
        viewModelScope.launch {
            _state.update {
                it.copy(list = emptyList())
            }
        }
    }

}