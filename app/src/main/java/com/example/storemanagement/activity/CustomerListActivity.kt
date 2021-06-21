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
import com.example.storemanagement.R
import com.example.storemanagement.adapter.CustomerAdapter
import com.example.storemanagement.listener.CustomerListListener
import com.example.storemanagement.listener.SwipeToDeleteListener
import com.example.storemanagement.model.Customer
import com.example.storemanagement.utilities.Constants
import com.example.storemanagement.viewmodel.CustomerViewModel
import kotlinx.android.synthetic.main.activity_customer_list.*

class CustomerListActivity:BaseActivity(),CustomerListListener {

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,CustomerListActivity::class.java)
        }
    }

    private lateinit var customerViewModel: CustomerViewModel
    private var userId:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)

        val customerAdapter= CustomerAdapter(this)
        rvCustomer.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvCustomer.adapter=customerAdapter

        customerViewModel= ViewModelProvider(this).get(CustomerViewModel::class.java)

        val pref=getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        userId=pref.getInt(Constants.KEY_USER_ID,-1)

        customerViewModel.getCustomerList(userId!!)

        val swipeToDeleteListener=object :SwipeToDeleteListener(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val item=customerAdapter.getItemAt(position)
                item.id?.let { removeCustomer(it) }
                customerAdapter.deleteItem(position)
            }
        }

        val itemTouchHelper=ItemTouchHelper(swipeToDeleteListener)
        itemTouchHelper.attachToRecyclerView(rvCustomer)

        customerViewModel.customerList.observe(this, Observer {customerList->
            customerAdapter.setNewData(customerList)
        })

        customerViewModel.isLoading.observe(this, Observer{ isLoading->
            if (isLoading){
                progressBar.visibility= View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        customerViewModel.error.observe(this, Observer { error->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        customerViewModel.isDelete.observe(this, Observer {isDelete->
            if (isDelete){
                val pref=getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
                val userId=pref.getInt(Constants.KEY_USER_ID,-1)

                customerViewModel.getCustomerList(userId)
            }
        })

        tbCustomerList.setNavigationOnClickListener {
            onBackPressed()
        }

        tbCustomerList.setOnMenuItemClickListener {
            startActivity(AddCustomerActivity.newIntent(this))
            true
        }
    }

    override fun onStart() {
        super.onStart()
        customerViewModel.getCustomerList(userId!!)
    }

    private fun removeCustomer(customerId:Int){
        customerViewModel.removeCustomer(customerId)

        customerViewModel.responseMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        customerViewModel.isLoading.observe(this, Observer {
            if(it){
                progressBar.visibility=View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        customerViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCustomerClick(item: Customer) {
        val intent=CartActivity.newIntent(this)
        intent.putExtra("id",item.id)
        startActivity(intent)
    }
}