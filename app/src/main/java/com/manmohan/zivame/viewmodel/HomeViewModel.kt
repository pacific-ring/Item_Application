package com.manmohan.zivame.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manmohan.zivame.model.Products
import com.manmohan.zivame.repository.ProductRepository
import kotlinx.coroutines.launch


class HomeViewModel(private val productRepository: ProductRepository) : ViewModel() {


    private val _products = MutableLiveData<Products>()
    val product : LiveData<Products>
        get() = _products


   fun getProductData() {
       viewModelScope.launch {
           _products.postValue(productRepository.getProductList())
       }
   }


}