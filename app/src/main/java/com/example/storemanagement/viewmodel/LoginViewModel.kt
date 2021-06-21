package com.example.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.LoginRequest
import com.example.storemanagement.model.LoginResponse
import com.example.storemanagement.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel:BaseViewModel() {
    val isLoading= MutableLiveData<Boolean>()
    val error= MutableLiveData<String>()
    val user= MutableLiveData<User>()

    fun login(req: LoginRequest){
        isLoading.value=true
        RestClient.getApiService()
            .login(req)
            .enqueue(object: Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    isLoading.value=false
                    error.value=t.message?:"Unknown Error"
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful){
                        isLoading.value=false
                        response.body()?.let {body->
//                            error.value=body.error?.firstOrNull()?.errorMessage?:"Unknown Error"
                            user.value=body.data?: User()
                        }
                    }
                }
            })
    }
}