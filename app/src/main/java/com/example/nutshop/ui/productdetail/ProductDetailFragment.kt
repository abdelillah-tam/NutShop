package com.example.nutshop.ui.productdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.nutshop.R
import com.example.nutshop.databinding.FragmentProductDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    val args: ProductDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentProductDetailBinding

    private val productDetailViewModel: ProductDetailViewModel by viewModels()

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductDetailBinding.bind(view)

        val product = args.product

        productDetailViewModel.showProductOnScreen(product)
        binding.quantityyouwant.text = "0"

        binding.plusQuantity.setOnClickListener {
            val currentQuantity = binding.quantityyouwant.text.toString().toInt()
            val newQuantity = changeQuantity(currentQuantity, 1)
            if (newQuantity <= product!!.quantity) binding.quantityyouwant.text =
                newQuantity.toString()
        }

        binding.minusQuantity.setOnClickListener {
            val currentQuantity = binding.quantityyouwant.text.toString().toInt()
            val newQuantity = changeQuantity(currentQuantity, -1)
            if (newQuantity >= 0) binding.quantityyouwant.text = newQuantity.toString()
        }


        binding.addtocart.setOnClickListener {
            val finalQuantity = binding.quantityyouwant.text.toString().toInt()
            productDetailViewModel.addToCart(
                product!!,
                finalQuantity
            )
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                productDetailViewModel.state.collect { ui ->
                    binding.detailProductTitle.text = ui.product.productName
                    binding.detailProductDescription.text = ui.product.description
                    binding.detailProductPrice.text = "$${ui.product.price}"
                    Picasso.get().load(ui.product.productPictureLink).into(binding.detailProductImage)

                    if (!ui.firstRun) {
                        if (ui.addedToCart) {
                            Snackbar.make(
                                binding.addtocart,
                                "Product is added to your shopcart",
                                Snackbar.LENGTH_SHORT
                            )
                                .setBackgroundTint(resources.getColor(R.color.green)).show()
                            binding.quantityyouwant.text = "0"
                        } else {
                            Snackbar.make(
                                binding.addtocart,
                                "Failed, try again!",
                                Snackbar.LENGTH_SHORT
                            )
                                .setBackgroundTint(resources.getColor(R.color.red)).show()
                        }
                    }
                }
            }
        }


    }


    fun changeQuantity(quantityNumber: Int, quantityAdded: Int): Int {
        return quantityNumber + quantityAdded
    }
}