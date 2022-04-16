package com.manmohan.zivame.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Item(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("rating")
    val rating: Int,

    var qty : Int = 1
) : Serializable