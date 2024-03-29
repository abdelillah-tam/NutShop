package com.example.nutshop.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.nutshop.R
import com.example.nutshop.domain.models.Product
import com.google.android.material.checkbox.MaterialCheckBox
import com.squareup.picasso.Picasso
import javax.inject.Inject

class HomeRecyclerAdapter @Inject constructor() : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {

    private var productList = emptyList<Product?>()
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeFragment: HomeFragment

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList.get(position)
        val productImage = holder.itemView.findViewById(R.id.product_image) as ImageView
        val productTitle = holder.itemView.findViewById(R.id.product_title) as TextView
        val productPrice = holder.itemView.findViewById(R.id.product_price) as TextView
        val productWishList = holder.itemView.findViewById(R.id.product_wishlist) as MaterialCheckBox

        productTitle.text = product?.productName
        productPrice.text = "$${product?.price}"
        if(!product?.productPictureLink.equals("")) Picasso.get().load(product?.productPictureLink).into(productImage)

        (holder.itemView.findViewById(R.id.thisforclicking) as ConstraintLayout).setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(product)
            homeFragment.findNavController().navigate(action)
        }

        productWishList.isChecked = product?.favorite == true
        productWishList.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (compoundButton.isPressed) {
                if (isChecked) {
                    homeViewModel.addToWishList(product!!)
                } else {
                    homeViewModel.deleteFromWishList(product!!)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return productList.size
    }


    fun setData(list : List<Product?>){
        productList = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int) : Product?{
        return productList[position]
    }

    fun setViewModel(homeViewModel: HomeViewModel, homeFragment: HomeFragment){
        this.homeViewModel = homeViewModel
        this.homeFragment = homeFragment
    }
}