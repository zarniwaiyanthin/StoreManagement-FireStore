package com.example.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.AddCustomerRequest
import com.example.storemanagement.model.AddCustomerResponse
import com.example.storemanagement.model.Customer
import com.example.storemanagement.model.CustomerListResponse
import com.example.storemanagement.model.RemoveResponse
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerViewModel:BaseViewModel() {
    val isLoading= MutableLiveData<Boolean>()
    val errorMessage= MutableLiveData<String>()
    val customerList= MutableLiveData<List<Customer>>()
    val responseMessage=MutableLiveData<String>()
    val isDelete=MutableLiveData<Boolean>()

    fun getCustomerList(userId:Int){
        isLoading.value=true

        FirebaseFirestore.getInstance().collection("customers")
            .addSnapshotListener { value, error ->
                isLoading.value=false
                if (value==null || value.isEmpty){
                    errorMessage.value=error.toString()
                }else{
                    val customers= mutableListOf<Customer>()
                    for (document in value.documents){
                        customers.add(
                            Customer(
                                id = document["id"] as Int,
                                name = document["name"] as String,
                                phoneNo = document["phoneNo"] as String
                            )
                        )
                    }
                    customerList.value=customers
                }
            }
    }

    fun addCustomer(req: AddCustomerRequest){
        isLoading.value=true

        FirebaseFirestore.getInstance().collection("customers")
            .add(
                mapOf(
                    "id" to req.userId,
                    "name" to req.name,
                    "phoneNo" to req.phoneNo
                )
            )
            .addOnCompleteListener {
                isLoading.value=false
                if(it.isSuccessful){
                    responseMessage.value="Success"
                }else{
                    errorMessage.value=it.exception?.message?:"Unknown Error"
                }
            }
    }

    fun removeCustomer(customerId:Int){
        isLoading.value=true

        FirebaseFirestore.getInstance().collection("customers")
            .whereEqualTo("id",customerId)
            .get()
            .addOnCompleteListener {
                isLoading.value=false
                if (it.isSuccessful){
                    for (document in it.result!!){
                        FirebaseFirestore.getInstance().collection("customers").document(document.id)
                    }
                    responseMessage.value="Success"
                }else{
                    errorMessage.value="Error"
                }
            }
    }
}