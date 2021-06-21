package com.example.storemanagement.viewmodel

import android.net.DnsResolver
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.RegisterRequest
import com.example.storemanagement.model.RegisterResponse
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel:BaseViewModel() {
    val isLoading=MutableLiveData<Boolean>()
    val error=MutableLiveData<String>()
    val responseMessage=MutableLiveData<String>()

    fun registerUser(req:RegisterRequest){
        isLoading.value=true

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(req.name!!,req.password!!)
            .addOnCompleteListener(){
                if (it.isSuccessful){
                    isLoading.value=false
                    responseMessage.value="Success Registration"
                }else{
                    isLoading.value=false
                    responseMessage.value="Fail Registration"
                }
            }
    }
}