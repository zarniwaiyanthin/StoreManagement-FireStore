package com.example.storemanagement.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storemanagement.model.Product
import com.example.storemanagement.R
import com.example.storemanagement.activity.ProductListActivity
import com.example.storemanagement.listener.ProductListListener
import kotlinx.android.synthetic.main.item_product.view.*

class ProductAdapter(private val listener: ProductListListener):BaseAdapter<Product,ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view=getLayoutInflater(parent).inflate(R.layout.item_product,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item=itemList.get(position)
        holder.itemView.tvProductName.text=item.name
        holder.itemView.tvProductPrice.text=item.price
        holder.itemView.setOnClickListener {
            listener.onProductClick(item)
        }
    }
}