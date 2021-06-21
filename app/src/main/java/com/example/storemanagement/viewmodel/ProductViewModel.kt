package com.example.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.storemanagement.data.remote.RestClient
import com.example.storemanagement.data.request.Add2CartRequest
import com.example.storemanagement.data.request.AddProductRequest
import com.example.storemanagement.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel:BaseViewModel() {
    val isLoading=MutableLiveData<Boolean>()
    val errorMessage=MutableLiveData<String>()
    val productList=MutableLiveData<List<Product>>()
    val responseMessage=MutableLiveData<String>()

    fun getProductList(userId:Int){
        isLoading.value=true

        FirebaseFirestore.getInstance().collection("products")
            .addSnapshotListener { value, error ->
                isLoading.value=false
                if (value==null || value.isEmpty){

                    errorMessage.value=error.toString()

                }else{

                    val products= mutableListOf<Product>()
                    for (document in value.documents){
                        products.add(
                            Product(
                                productId = document["productId"] as String,
                                name = document["name"] as String,
                                price = document["price"] as String
                            )
                        )
                    }
                    productList.value=products
                }
            }
    }

    fun add2Cart(req: Add2CartRequest){
        isLoading.value=true

        FirebaseFirestore.getInstance().collection("customers").document("id").collection("cart")
            .add(
                mapOf(
                    "customerId" to req.customerId,
                    "productIds" to req.productIds
                )
            )
            .addOnCompleteListener {
                isLoading.value=false
                if (it.isSuccessful){
                    responseMessage.value="Success"
                }else{
                    errorMessage.value=it.exception?.message?:"Unknown Error"
                }
            }
    }

    fun addProduct(req: AddProductRequest){
        isLoading.value=true

        FirebaseFirestore.getInstance().collection("products")
            .add(
                mapOf(
                    "userId" to req.userId,
                    "name" to req.name,
                    "price" to req.price
                )
            )
            .addOnCompleteListener {
                isLoading.value=false
                if (it.isSuccessful){
                    responseMessage.value="Success"
                }else{
                    errorMessage.value=it.exception?.message?:"Unknown Error"
                }
            }
    }

    fun removeProduct(productId:Int){
        isLoading.value=true

        FirebaseFirestore.getInstance().collection("products")
            .get()
            .addOnCompleteListener {
                isLoading.value=false
                if (it.isSuccessful){
                    for (document in it.result!!){
                        FirebaseFirestore.getInstance().collection("products").document(document.id).delete()
                    }
                    responseMessage.value="Success"
                }else{
                    errorMessage.value="Error"
                }
            }
    }
}