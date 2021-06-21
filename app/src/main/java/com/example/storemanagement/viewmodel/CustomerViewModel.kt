package com.example.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.AddCustomerRequest
import com.example.storemanagement.model.AddCustomerResponse
import com.example.storemanagement.model.Customer
import com.example.storemanagement.model.CustomerListResponse
import com.example.storemanagement.model.RemoveResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerViewModel:BaseViewModel() {
    val isLoading= MutableLiveData<Boolean>()
    val error= MutableLiveData<String>()
    val customerList= MutableLiveData<List<Customer>>()
    val responseMessage=MutableLiveData<String>()

    fun getCustomerList(userId:Int){
        isLoading.value=true
        RestClient.getApiService()
            .getCustomerList(userId)
            .enqueue(object :Callback<CustomerListResponse>{
                override fun onFailure(call: Call<CustomerListResponse>, t: Throwable) {
                    isLoading.value=false
                    error.value=t.message?:"Unknown Error"
                }

                override fun onResponse(
                    call: Call<CustomerListResponse>,
                    response: Response<CustomerListResponse>
                ) {
                    if (response.isSuccessful){
                        isLoading.value=false
                        response.body()?.let {body->
                            customerList.value=body.data?: listOf()
                            error.value=body.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                        }
                    }
                }
            })
    }

    fun addCustomer(req: AddCustomerRequest){
        isLoading.value=true
        RestClient.getApiService()
                .addCustomer(req)
                .enqueue(object :Callback<AddCustomerResponse>{
                    override fun onFailure(call: Call<AddCustomerResponse>, t: Throwable) {
                        isLoading.value=false
                        error.value=t.message?:"Unknown Error"
                    }

                    override fun onResponse(call: Call<AddCustomerResponse>, response: Response<AddCustomerResponse>) {
                        if (response.isSuccessful){
                            isLoading.value=false
                            response.body()?.let {
                                responseMessage.value=it.responseMessage
                                error.value=it.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                            }
                        }
                    }
                })
    }

    fun removeCustomer(customerId:Int){
        isLoading.value=true
        RestClient.getApiService()
                .removeCustomer(customerId)
                .enqueue(object:retrofit2.Callback<RemoveResponse>{
                    override fun onFailure(call: Call<RemoveResponse>, t: Throwable) {
                        isLoading.value=false
                        error.value=t.message?:"Unknown Error"
                    }

                    override fun onResponse(call: Call<RemoveResponse>, response: Response<RemoveResponse>) {
                        isLoading.value=false
                        if (response.isSuccessful){
                            response.body()?.let {
                                responseMessage.value=it.responseMessage
                                error.value=it.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                            }
                        }
                    }
                })
    }

    private fun generateCustomerList()= listOf<Customer>(Customer(1,"lee","000093241"),
            Customer(2,"low","0921412341"),
            Customer(2,"low","0921412341"),
            Customer(2,"low","0921412341"),
            Customer(2,"low","0921412341"),
            Customer(2,"low","0921412341"),
            Customer(2,"low","0921412341"),
            Customer(2,"low","0921412341"))
}