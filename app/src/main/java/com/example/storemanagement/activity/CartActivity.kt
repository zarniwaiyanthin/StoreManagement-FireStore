package com.example.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storemanagement.R
import com.example.storemanagement.adapter.CartAdapter
import com.example.storemanagement.data.request.Add2CartRequest
import com.example.storemanagement.utilities.Constants
import com.example.storemanagement.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity:BaseActivity() {

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,CartActivity::class.java)
        }
    }

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        productViewModel= ViewModelProvider(this).get(ProductViewModel::class.java)

        val pref=getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        val userId=pref.getInt(Constants.KEY_USER_ID,-1)

        val customerId=Intent().getIntExtra("id",1)

        val chooseProductAdapter= CartAdapter()
        rvCart.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvCart.adapter=chooseProductAdapter

        productViewModel.getProductList(userId)

        productViewModel.productList.observe(this, Observer {
            if (userId>0){
                chooseProductAdapter.setNewData(it)
            }
        })

        productViewModel.isLoading.observe(this, Observer {
            if (it){
                progressBar.visibility=View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        productViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        btnDone.setOnClickListener {
            val productIdList= mutableListOf<Int>()
            for (product in chooseProductAdapter.selectedItemList){
                productIdList.add(product.productId!!.toInt())
            }
            val req=Add2CartRequest(
                    customerId = customerId,
                    productIds = productIdList
            )

            chooseProduct(req)
        }

        tbCart.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun chooseProduct(req:Add2CartRequest){
        productViewModel.add2Cart(req)

        productViewModel.responseMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        productViewModel.isLoading.observe(this, Observer {
            if (it){
                progressBar.visibility= View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        productViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }
}