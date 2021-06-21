package com.example.storemanagement.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T,VH:RecyclerView.ViewHolder>:RecyclerView.Adapter<VH>() {

    protected val itemList= mutableListOf<T>()

    protected fun getLayoutInflater(view:ViewGroup): LayoutInflater {
        return LayoutInflater.from(view.context)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setNewData(newItems:List<T>){
        itemList.clear()
        itemList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItemAt(position:Int): T {
        return itemList[position]
    }

    fun deleteItem(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}