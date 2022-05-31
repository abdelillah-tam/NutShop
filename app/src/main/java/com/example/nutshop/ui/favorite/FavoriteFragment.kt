package com.example.nutshop.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding : FragmentFavoriteBinding

    private val favoriteViewModel : FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter : FavoriteListAdapter


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        favoriteAdapter = FavoriteListAdapter()
        favoriteAdapter.setViewModel(favoriteViewModel)
        favoriteViewModel.getProductsInFavorite()

        binding.favoriteList.let {
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            it.adapter = favoriteAdapter

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                favoriteViewModel.state.collect{
                    favoriteAdapter.setList(it.list)
                }
            }
        }

    }
}