package com.example.nutshop.ui.shopcart

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentShopcartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShopcartFragment : Fragment(R.layout.fragment_shopcart) {

    lateinit var listAdapter : ShopcartListAdapter
    private lateinit var binding : FragmentShopcartBinding

    val shopcartViewModel : ShopcartViewModel by activityViewModels()
    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShopcartBinding.bind(view)
        listAdapter = ShopcartListAdapter()
        listAdapter.setModel(shopcartViewModel)
        binding.listShopcart.also {
            it.adapter = listAdapter
            it.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        shopcartViewModel.getProductsFromShopcart()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                shopcartViewModel.state.collect{
                    listAdapter.setProducts(it.list)
                    if (!it.list.isEmpty()) {
                        var total: Double = 0.00
                        it.list.forEach {
                            total += it!!.quantityTaken * it.price
                        }
                        binding.totalprice.text = "${total}$"
                    }
                }
            }
        }

    }
}