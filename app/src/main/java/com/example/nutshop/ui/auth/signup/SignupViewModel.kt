package com.example.nutshop.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutshop.data.source.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {


    private val _state = MutableStateFlow(false)
    val state = _state.asStateFlow()


    fun signup(email: String, password: String){
        viewModelScope.launch{
            customerRepository.signup(email, password).collect{ result ->
                _state.update {
                    result
                }
            }
        }
    }
}