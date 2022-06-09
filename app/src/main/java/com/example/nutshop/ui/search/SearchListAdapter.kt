package com.example.nutshop.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nutshop.R
import com.example.nutshop.domain.models.Product
import com.squareup.picasso.Picasso

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    private var list : MutableList<Product?> = mutableListOf()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = list[position]
        val productTitle = holder.itemView.findViewById(R.id.product_searched_name) as TextView
        val productImage = holder.itemView.findViewById(R.id.product_searched_image) as ImageView
        productTitle.text = product?.productName

        Picasso.get().load(product?.productPictureLink).into(productImage)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: List<Product?>){
        val diffCallback = Diff(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

}

class Diff(
    var oldList: List<Product?>,
    var newList: List<Product?>
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