package com.manmohan.zivame.utils

object Constants {
    const val BASE_URL = "https://my-json-server.typicode.com/"
    const val PRODUCT_KEY = "productList"
}

object Operations {
    fun findDifferentiatorPos(input : String) : Int {
        return input.indexOf('(')
    }
}