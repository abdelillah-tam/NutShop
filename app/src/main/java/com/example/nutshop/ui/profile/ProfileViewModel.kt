package com.example.nutshop.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutshop.data.source.CustomerRepository
import com.example.nutshop.domain.models.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ProfileViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _state = MutableStateFlow<Customer?>(Customer())
    val state = _state.asStateFlow()

    fun getProfileInformation() {
        viewModelScope.launch {
            customerRepository.getProfileInformation().collect{ result ->
                _state.update {
                    result
                }
            }
        }
    }

}