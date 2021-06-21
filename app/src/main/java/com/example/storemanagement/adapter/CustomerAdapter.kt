package com.example.storemanagement.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storemanagement.R
import com.example.storemanagement.listener.CustomerListListener
import com.example.storemanagement.model.Customer
import kotlinx.android.synthetic.main.item_customer.view.*

class CustomerAdapter(private val listener:CustomerListListener):BaseAdapter<Customer,CustomerAdapter.CustomerViewHolder>() {

    class CustomerViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)!!
        val view=layoutInflater.inflate(R.layout.item_customer,parent,false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item= itemList[position]
        holder.itemView.tvUserName.text=item.name
        holder.itemView.tvPh.text=item.phoneNo
        holder.itemView.setOnClickListener {
            listener.onCustomerClick(item)
        }
    }
}