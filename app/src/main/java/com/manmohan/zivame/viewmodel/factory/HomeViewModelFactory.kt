package com.manmohan.zivame.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manmohan.zivame.repository.ProductRepository
import com.manmohan.zivame.viewmodel.HomeViewModel
import javax.inject.Inject


class HomeViewModelFactory @Inject constructor(private val productRepository: ProductRepository) :
ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(productRepository) as T
    }

}