package com.example.nutshop.ui.wishlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nutshop.R
import com.example.nutshop.domain.models.Product
import com.google.android.material.checkbox.MaterialCheckBox
import com.squareup.picasso.Picasso

class FavoriteListAdapter : RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>() {

    private var list = mutableListOf<Product?>()
    private lateinit var wishlistViewModel: WishlistViewModel

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]

        val productImage = holder.itemView.findViewById(R.id.favorite_product_image) as ImageView
        val productTitle = holder.itemView.findViewById(R.id.favorite_product_title) as TextView
        val productPrice = holder.itemView.findViewById(R.id.favorite_product_price) as TextView
        val productFavorite = holder.itemView.findViewById(R.id.favorite_product_favorite) as MaterialCheckBox

        Picasso.get().load(product?.productPictureLink).into(productImage)
        productTitle.text = product?.productName
        productPrice.text = "${product?.price}"
        if (product!!.favorite){
            productFavorite.isChecked = true
        }
        productFavorite.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (!isChecked){
                wishlistViewModel.deleteFromFavorite(product!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun setList(list: List<Product?>) {
        val diff = Diff(this.list, list)
        val result  = DiffUtil.calculateDiff(diff)

        this.list.clear()
        this.list.addAll(list)
        result.dispatchUpdatesTo(this)
    }

    fun setViewModel(wishlistViewModel: WishlistViewModel){
        this.wishlistViewModel = wishlistViewModel
    }
}

class Diff(
    val oldList: List<Product?>,
    val newList: List<Product?>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.productId == newList[newItemPosition]?.productId
     }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]!!.equals(newList[newItemPosition])
    }

}