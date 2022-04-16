package com.manmohan.zivame.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.manmohan.zivame.R
import com.manmohan.zivame.databinding.ActivityMainBinding
import com.manmohan.zivame.di.components.DaggerHomeScreenActivityComponent
import com.manmohan.zivame.di.components.HomeScreenActivityComponent
import com.manmohan.zivame.model.Item
import com.manmohan.zivame.model.Products
import com.manmohan.zivame.ui.adapters.ProductListAdapter
import com.manmohan.zivame.viewmodel.HomeViewModel
import com.manmohan.zivame.viewmodel.factory.HomeViewModelFactory
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {
    private lateinit var homeScreenActivityComponent: HomeScreenActivityComponent
    private lateinit var homeActivityViewModel: HomeViewModel
    private lateinit var binding : ActivityMainBinding
    private lateinit var homeItemAdapter : ProductListAdapter

    companion object{
        private val itemSelected : ArrayList<Item> = arrayListOf()
    }

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
                initializeRecyclerView(it!!)
       })
    }

    private fun initializeRecyclerView(it: Products) {
        homeItemAdapter = ProductListAdapter(it, itemSelected, object :
            ProductListAdapter.OnItemClick {
            override fun onClick(item: Item) {
                itemAddedToCart(item)
            }
        })
        binding.productListRv.apply {
            this.layoutManager = LinearLayoutManager(this@HomeActivity)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = homeItemAdapter
        }
    }

    private fun itemAddedToCart(item: Item) {
        if (!itemSelected.contains(item)) {
            itemSelected.add(item)
            homeItemAdapter.notifyDataSetChanged()
        }
    }


}