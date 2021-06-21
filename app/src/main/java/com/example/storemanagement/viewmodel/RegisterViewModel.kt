package com.example.storemanagement.viewmodel

import android.net.DnsResolver
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.RegisterRequest
import com.example.storemanagement.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel:BaseViewModel() {
    val isLoading=MutableLiveData<Boolean>()
    val error=MutableLiveData<String>()
    val responseMessage=MutableLiveData<String>()

    fun registerUser(req:RegisterRequest){
        isLoading.value=true
        RestClient.getApiService()
                .registerUser(req)
                .enqueue(object :Callback<RegisterResponse>{
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        isLoading.value=false
                        error.value=t.message?:"Unknown Error"
                    }

                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        isLoading.value=false
                        if (response.isSuccessful){
                            response.body()?.let {
                                responseMessage.value=it.responseMessage
//                                error.value=it.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                            }
                        }
                    }
                })
    }
}