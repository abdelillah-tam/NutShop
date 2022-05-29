package com.example.nutshop.ui.auth.signup

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
import com.example.nutshop.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding
    private val signupViewModel : SignupViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
        lifecycleScope.launch {
            signupViewModel.state.collect{
                if (it){
                    Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Unsuccessful opertation, try again please!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.signupButton.setOnClickListener {
            val email = binding.emailEditTextSignup.text.toString()
            val password = binding.passwordEditTextSignup.text.toString()

            signupViewModel.signup(email, password)
        }

        binding.loginTextViewSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
    }
}