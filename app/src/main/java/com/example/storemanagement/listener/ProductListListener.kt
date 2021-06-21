package com.example.storemanagement.listener

import com.example.storemanagement.model.Product

interface ProductListListener {
    fun onProductClick(item:Product)
}