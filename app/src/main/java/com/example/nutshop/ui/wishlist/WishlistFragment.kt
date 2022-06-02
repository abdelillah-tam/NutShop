package com.example.nutshop.ui.wishlist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentWishlistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class WishlistFragment : Fragment(R.layout.fragment_wishlist) {

    private lateinit var binding : FragmentWishlistBinding

    private val wishlistViewModel : WishlistViewModel by activityViewModels()
    private lateinit var favoriteAdapter : FavoriteListAdapter


    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWishlistBinding.bind(view)

        favoriteAdapter = FavoriteListAdapter()
        favoriteAdapter.setViewModel(wishlistViewModel)
        wishlistViewModel.getProductsInFavorite()

        binding.favoriteList.let {
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            it.adapter = favoriteAdapter

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                wishlistViewModel.state.collect{
                    favoriteAdapter.setList(it.list)
                }
            }
        }

    }
}