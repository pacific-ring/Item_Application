package com.manmohan.zivame.retrofit

import com.manmohan.zivame.model.Products
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("nancymadan/assignment/db")
    suspend fun getProductList() : Response<Products>

}