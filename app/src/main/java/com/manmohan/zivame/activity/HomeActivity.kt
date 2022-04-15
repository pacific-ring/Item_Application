package com.manmohan.zivame.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.manmohan.zivame.R
import com.manmohan.zivame.databinding.ActivityMainBinding
import com.manmohan.zivame.di.components.DaggerHomeScreenActivityComponent
import com.manmohan.zivame.di.components.HomeScreenActivityComponent
import com.manmohan.zivame.viewmodel.HomeViewModel
import com.manmohan.zivame.viewmodel.factory.HomeViewModelFactory
import dagger.Component
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {
    lateinit var homeScreenActivityComponent: HomeScreenActivityComponent
    lateinit var homeActivityViewModel: HomeViewModel
    lateinit var binding : ActivityMainBinding

    @Inject
    lateinit var homeActivityViewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initialize()
        initializeViewModel()
        fetchProductData()
        setObservers()

    }

    private fun initialize() {
        homeScreenActivityComponent = DaggerHomeScreenActivityComponent.builder().build()
        homeScreenActivityComponent.inject(this)
    }

    private fun initializeViewModel() {
        homeActivityViewModel = ViewModelProvider(this, homeActivityViewModelFactory)
            .get(HomeViewModel :: class.java)
    }

    private fun fetchProductData() = homeActivityViewModel.getProductData()

    private fun setObservers() {
       homeActivityViewModel.product.observe(this, Observer {

       })
    }
}