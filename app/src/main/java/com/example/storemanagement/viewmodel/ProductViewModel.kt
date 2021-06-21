package com.example.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.Add2CartRequest
import com.example.storemanagement.data.request.AddProductRequest
import com.example.storemanagement.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel:BaseViewModel() {
    val isLoading=MutableLiveData<Boolean>()
    val error=MutableLiveData<String>()
    val productList=MutableLiveData<List<Product>>()
    val responseMessage=MutableLiveData<String>()

    fun getProductList(userId:Int){
        isLoading.value=true
        RestClient.getApiService()
                .getProductList(userId)
                .enqueue(object :Callback<ProductListResponse>{
                    override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                        isLoading.value=false
                        error.value=t.message?:"Unknown Error"
                    }

                    override fun onResponse(call: Call<ProductListResponse>, response: Response<ProductListResponse>) {
                        if(response.isSuccessful){
                            isLoading.value=false
                            response.body()?.let { body->
                                productList.value=body.data?: listOf()
 //                               error.value=body.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                            }
                        }
                    }
                })
    }

    fun add2Cart(req: Add2CartRequest){
        isLoading.value=true
        RestClient.getApiService()
                .add2Cart(req)
                .enqueue(object :Callback<Add2CartResponse>{
                    override fun onFailure(call: Call<Add2CartResponse>, t: Throwable) {
                        isLoading.value=false
                        error.value=t.message?:"Unknown Error"
                    }

                    override fun onResponse(call: Call<Add2CartResponse>, response: Response<Add2CartResponse>) {
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

    fun addProduct(req: AddProductRequest){
        isLoading.value=true
        RestClient.getApiService()
                .addProduct(req)
                .enqueue(object :Callback<ProductListResponse>{
                    override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                        isLoading.value=false
                        error.value=t.message?:"Unknown Error"
                    }

                    override fun onResponse(call: Call<ProductListResponse>, response: Response<ProductListResponse>) {
                        if (response.isSuccessful){
                            isLoading.value=false
                            response.body()?.let {body->
                                responseMessage.value=body.responseMessage?:"N/A"
//                                error.value=body.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                            }
                        }
                    }
                })
    }

    fun removeProduct(productId:Int){
        isLoading.value=true
        RestClient.getApiService()
                .removeProduct(productId=productId)
                .enqueue(object :Callback<RemoveResponse>{
                    override fun onFailure(call: Call<RemoveResponse>, t: Throwable) {
                        isLoading.value=false
                        error.value=t.message?:"Unknown Error"
                    }

                    override fun onResponse(call: Call<RemoveResponse>, response: Response<RemoveResponse>) {
                        isLoading.value=false
                        if (response.isSuccessful){
                            response.body()?.let {
                                responseMessage.value=it.responseMessage
 //                               error.value=it.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                            }
                        }
                    }
                })
    }
}