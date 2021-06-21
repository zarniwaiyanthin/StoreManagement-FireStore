package com.example.storemanagement.data.remote

import com.example.storemanagement.data.request.*
import com.example.storemanagement.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("user/login")
    fun login(@Body req:LoginRequest):Call<LoginResponse>

    @POST("customer/list")
    fun getCustomerList(@Header("userId") userId:Int):Call<CustomerListResponse>

    @POST("product/list")
    fun getProductList(@Header("userId") userId: Int):Call<ProductListResponse>

    @POST("product/add")
    fun addProduct(@Body req:AddProductRequest):Call<ProductListResponse>

    @POST("customer/add")
    fun addCustomer(@Body req: AddCustomerRequest):Call<AddCustomerResponse>

    @POST("customer/remove")
    fun removeCustomer(@Header("customerId") customerId:Int):Call<RemoveResponse>

    @POST("product/remove")
    fun removeProduct(@Header("productId") productId:Int):Call<RemoveResponse>

    @POST("cart/add")
    fun add2Cart(@Body req:Add2CartRequest):Call<Add2CartResponse>

    @POST("user/register")
    fun registerUser(@Body req:RegisterRequest): Call<RegisterResponse>
}