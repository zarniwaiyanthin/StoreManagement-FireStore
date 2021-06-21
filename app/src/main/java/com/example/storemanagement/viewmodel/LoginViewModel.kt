package com.example.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.LoginRequest
import com.example.storemanagement.model.LoginResponse
import com.example.storemanagement.model.User
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel:BaseViewModel() {
    val isLoading= MutableLiveData<Boolean>()
    val error= MutableLiveData<String>()
    val user= MutableLiveData<String>()

    fun login(req: LoginRequest){
        isLoading.value=true

        FirebaseAuth.getInstance().signInWithEmailAndPassword(req.userName!!,req.password!!)
            .addOnCompleteListener(){
                if (it.isSuccessful){
                    isLoading.value=false
                    user.value=FirebaseAuth.getInstance().currentUser?.uid
                }else{
                    isLoading.value=false
                    error.value="Login Error"
                }
            }
    }
}