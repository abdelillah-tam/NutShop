package com.example.nutshop.ui.shopcart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nutshop.R
import com.example.nutshop.domain.models.Product
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso

class ShopcartListAdapter : RecyclerView.Adapter<ShopcartListAdapter.Holder>(){

    private var list : MutableList<Product?> = mutableListOf()

    private lateinit var shopcartViewModel: ShopcartViewModel

    class Holder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopcart_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val productImage = holder.itemView.findViewById(R.id.shapeableImageView) as ShapeableImageView
        val productTitle = holder.itemView.findViewById(R.id.shopcart_product_title) as TextView
        val productPrice = holder.itemView.findViewById(R.id.shopcart_product_price) as TextView
        val productQuantity = holder.itemView.findViewById(R.id.shopcart_product_quantity) as TextView
        val cancel = holder.itemView.findViewById(R.id.cancel) as MaterialButton

        val product = list.get(position)

        Picasso.get().load(product?.productPictureLink).into(productImage)
        productTitle.text = product?.productName
        productPrice.text = "${product?.price}$"
        productQuantity.text = "${product?.quantityTaken}"

        cancel.setOnClickListener {
            shopcartViewModel.deleteProductFromShopcart(product!!)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setProducts(list: List<Product?>){
        val diffCallback = DiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setModel(shopcartViewModel: ShopcartViewModel){
        this.shopcartViewModel = shopcartViewModel
    }


}

class DiffCallback(
    var oldList : List<Product?>,
    var newList : List<Product?>
) : DiffUtil.Callback(){

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