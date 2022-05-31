package com.example.nutshop.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.viewModels
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nutshop.domain.Category
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var productListAdapter : HomeRecyclerAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var gestureCompat: GestureDetectorCompat

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        productListAdapter = HomeRecyclerAdapter()
        productListAdapter.setViewModel(homeViewModel)
        gestureCompat = GestureDetectorCompat(requireContext(), object : GestureDetector.OnGestureListener{
            override fun onDown(p0: MotionEvent?): Boolean {
                return false
            }

            override fun onShowPress(p0: MotionEvent?) {

            }

            override fun onSingleTapUp(p0: MotionEvent?): Boolean {
                val viewPosition = binding.recyclerlistProductsHome.findChildViewUnder(p0!!.x, p0.y)
                if (viewPosition != null){
                    val product = productListAdapter.getItem(binding.recyclerlistProductsHome.getChildAdapterPosition(viewPosition))
                    val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
                    findNavController().navigate(action)
                }
                return false
            }

            override fun onScroll(
                p0: MotionEvent?,
                p1: MotionEvent?,
                p2: Float,
                p3: Float
            ): Boolean {

                return false
            }

            override fun onLongPress(p0: MotionEvent?) {
            }

            override fun onFling(
                p0: MotionEvent?,
                p1: MotionEvent?,
                p2: Float,
                p3: Float
            ): Boolean {
                return false
            }

        })


        binding.recyclerlistProductsHome.apply {
            adapter = productListAdapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            /*addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    gestureCompat.onTouchEvent(e)
                    return false
                }
            })*/
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