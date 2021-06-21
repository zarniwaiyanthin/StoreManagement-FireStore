package com.example.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.storemanagement.R
import com.example.storemanagement.data.request.AddProductRequest
import com.example.storemanagement.utilities.Constants
import com.example.storemanagement.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_add_product.*

class AddProductActivity:BaseActivity() {

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,AddProductActivity::class.java)
        }
    }

    private lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        productViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)

        val pref=getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        val userId=pref.getInt(Constants.KEY_USER_ID,-1)

        btnAddProduct.setOnClickListener {

            val name=etProductName.text.toString()
            val price=etPrice.text.toString().toIntOrNull()
            
            if (userId>0){
                val req=AddProductRequest(
                        name = name,
                        price = price,
                        userId = userId
                )
                productViewModel.addProduct(req)
            }
        }

        productViewModel.responseMessage.observe(this, Observer { addedSuccess->
            Toast.makeText(this, addedSuccess, Toast.LENGTH_SHORT).show()
        })

        productViewModel.isLoading.observe(this, Observer { isLoading->
            if (isLoading){
                progressBar.visibility= View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        productViewModel.error.observe(this, Observer { error->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        tbAddProduct.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}