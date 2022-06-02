package com.example.nutshop.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding : FragmentProfileBinding
    private val profileViewModel : ProfileViewModel by activityViewModels()
    

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        profileViewModel.getProfileInformation()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                profileViewModel.state.collect{
                    if (it != null) {
                        binding.profileFullname.text = "${it.firstName} ${it.lastName}"
                        binding.profileEmail.text = it.email
                        binding.profileAddress.text = it.address
                    }
                }
            }
        }
    }
}