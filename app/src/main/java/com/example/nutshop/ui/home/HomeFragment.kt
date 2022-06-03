package com.example.nutshop.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nutshop.domain.Category
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var productListAdapter : HomeRecyclerAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()

    //private lateinit var gestureCompat: GestureDetectorCompat

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        (activity as AppCompatActivity).setSupportActionBar(binding.materialToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val navHost =
            activity?.supportFragmentManager?.findFragmentById(R.id.fragment_container_for_fragments) as NavHostFragment
        (activity as AppCompatActivity).setupActionBarWithNavController(navHost.navController)


        productListAdapter = HomeRecyclerAdapter()
        productListAdapter.setViewModel(homeViewModel, this)


        binding.recyclerlistProductsHome.apply {
            adapter = productListAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }

        binding.category.check(R.id.category_whey)
        getProducts(binding.category.checkedRadioButtonId)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.state.collect {
                    productListAdapter.setData(it.listOfProducts)
                }
            }
        }

        binding.category.setOnCheckedChangeListener { radioGroup, checkedId ->
            getProducts(checkedId)
        }

        binding.searchedittext.setOnClickListener {
            findNavController().navigate(R.id.action_home_page_to_searchFragment)
        }
    }

    fun getProducts(checkedId: Int){
        when(checkedId){
            R.id.category_whey -> homeViewModel.chooseCategory(Category.WHEYPROTEIN)
            R.id.category_fat -> homeViewModel.chooseCategory(Category.FATBURNER)
            R.id.category_mass -> homeViewModel.chooseCategory(Category.MASSGAINER)
            R.id.category_pre -> homeViewModel.chooseCategory(Category.PREWORKOUT)
            R.id.category_prob -> homeViewModel.chooseCategory(Category.PROBIOTIC)
        }
    }
}