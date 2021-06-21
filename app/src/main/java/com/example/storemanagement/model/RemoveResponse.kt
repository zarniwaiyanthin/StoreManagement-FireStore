package com.example.storemanagement.model

import com.google.gson.annotations.SerializedName

class RemoveResponse (
        @SerializedName("responseCode") val responseCode:Int?,
        @SerializedName("responseMessage") val responseMessage:String?,
        @SerializedName("data") val data:Boolean?,
        @SerializedName("error") val error:List<ApiError>?
) {
}