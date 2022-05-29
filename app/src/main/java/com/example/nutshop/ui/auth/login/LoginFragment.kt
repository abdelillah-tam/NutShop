package com.example.nutshop.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        lifecycleScope.launch {
            loginViewModel.state.collect{
                if (it){
                    Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Unsuccessful opertation, try again please!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditTextLogin.text.toString()
            val password = binding.passwordEditTextLogin.text.toString()
            loginViewModel.login(email, password)
        }

        binding.signupTextViewLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
}