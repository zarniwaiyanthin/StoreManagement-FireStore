package com.example.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storemanagement.adapter.ProductAdapter
import com.example.storemanagement.R
import com.example.storemanagement.listener.ProductListListener
import com.example.storemanagement.listener.SwipeToDeleteListener
import com.example.storemanagement.model.Product
import com.example.storemanagement.utilities.Constants
import com.example.storemanagement.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_product_list.*

class ProductListActivity:BaseActivity(),ProductListListener {

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,ProductListActivity::class.java)
        }
    }

    private lateinit var productViewModel: ProductViewModel
    private var userId:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        productViewModel= ViewModelProvider(this).get(ProductViewModel::class.java)

        val productAdapter= ProductAdapter(this)
        rvProduct.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvProduct.adapter=productAdapter

        val pref=getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        userId=pref.getInt(Constants.KEY_USER_ID,-1)

        productViewModel.getProductList(userId!!)

        productViewModel.productList.observe(this, Observer { productList->
            productAdapter.setNewData(productList)
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

        tbProductList.setNavigationOnClickListener {
            onBackPressed()
        }

        tbProductList.setOnMenuItemClickListener {
            startActivity(AddProductActivity.newIntent(this))
            true
        }

        val swipeToDeleteListener=object :SwipeToDeleteListener(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val item=productAdapter.getItemAt(position)
                removeProduct(item.productId!!.toInt())
                productAdapter.deleteItem(position)
            }
        }

        val itemTouchHelper=ItemTouchHelper(swipeToDeleteListener)
        itemTouchHelper.attachToRecyclerView(rvProduct)

    }

    override fun onStart() {
        super.onStart()
        productViewModel.getProductList(userId!!)
    }

    private fun removeProduct(productId:Int){
        productViewModel.removeProduct(productId=productId)

        productViewModel.responseMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        productViewModel.isLoading.observe(this, Observer {
            if(it){
                progressBar.visibility=View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        productViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onProductClick(item: Product) {
        TODO("Not yet implemented")
    }
}