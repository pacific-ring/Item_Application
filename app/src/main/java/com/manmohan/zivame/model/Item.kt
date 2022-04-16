package com.manmohan.zivame.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("rating")
    val rating: Int
)