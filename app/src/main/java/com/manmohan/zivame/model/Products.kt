package com.manmohan.zivame.model


import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("products")
    val items: List<Item>
)