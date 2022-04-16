package com.manmohan.zivame.repository

import com.manmohan.zivame.model.Products
import com.manmohan.zivame.retrofit.ProductApi
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productApi: ProductApi) {

    suspend fun getProductList() : Products {
        val result = productApi.getProductList()

        if (result.isSuccessful && result.body() != null) {
           return result.body()!!
        } else {
            throw Exception("Product Data Fetched is null")
        }
    }
}