package com.example.storemanagement.data.request

import com.google.gson.annotations.SerializedName

class Add2CartRequest(
        @SerializedName("customerId") val customerId:Int?,
        @SerializedName("productIds") val productIds:MutableList<Int>?
) {
}