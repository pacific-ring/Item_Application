package com.manmohan.zivame.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
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
import java.io.Serializable
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {
    private lateinit var homeScreenActivityComponent: HomeScreenActivityComponent
    private lateinit var homeActivityViewModel: HomeViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeItemAdapter: ProductListAdapter
    private val itemSelected: ArrayList<Item> = arrayListOf()

    @Inject
    lateinit var homeActivityViewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initialize()
        initializeViewModel()
        fetchProductData()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.cartContainer.setOnClickListener {
            openCartScreen()
        }
    }

    /**
     * Method to initialise DaggerComponent
     */
    private fun initialize() {
        homeScreenActivityComponent = DaggerHomeScreenActivityComponent.builder().build()
        homeScreenActivityComponent.inject(this)
    }


    /**
     * Method to initialise ViewModel
     */
    private fun initializeViewModel() {
        homeActivityViewModel = ViewModelProvider(this, homeActivityViewModelFactory)
            .get(HomeViewModel::class.java)
    }

    /**
     * Method to fetch the Product List from  API
     */
    private fun fetchProductData() = homeActivityViewModel.getProductData()

    /**
     * Method to set observer on data change from API End
     */
    private fun setObservers() {
        homeActivityViewModel.product.observe(this, Observer {
            initializeRecyclerView(it!!)
        })
    }

    /**
     * Initialising recycler view and adapter
     * based upon the data fetched and also
     * receiving the callback from the item click on the adapter
     */
    private fun initializeRecyclerView(it: Products) {
        homeItemAdapter = ProductListAdapter(it, itemSelected, object :
            ProductListAdapter.OnItemClick {
            override fun onAdd(item: Item) {
                itemAddedToCart(item)
                setNotificationBadge()
            }

            override fun onRemove(item: Item) {
                removeItemFromCart(item)
                setNotificationBadge()
            }
        })

        binding.productListRv.apply {
            this.layoutManager = LinearLayoutManager(this@HomeActivity)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = homeItemAdapter
        }
    }

    // Actions performed when item is added to cart
    private fun itemAddedToCart(item: Item) {
        if (!itemSelected.contains(item)) {
            itemSelected.add(item)
            homeItemAdapter.notifyDataSetChanged()
        }
    }

    //Actions Performed when item is removed from the cart
    private fun removeItemFromCart(item: Item) {
        if (itemSelected.contains(item)) {
            itemSelected.remove(item)
            homeItemAdapter.notifyDataSetChanged()
        }
    }

    //Method to set the notification badges.
    private fun setNotificationBadge() {
        if (itemSelected.isNullOrEmpty()) {
            binding.notificationBadgeTv.visibility = View.GONE
        } else {
            binding.notificationBadgeTv.visibility = View.VISIBLE
            binding.selectedItemCount = itemSelected.size.toString()
        }
    }

    // Opens Cart Activity
    private fun openCartScreen() {
        val cartIntent: Intent = Intent(this, CartActivity::class.java)
        cartIntent.putExtra("productList", itemSelected as Serializable)
        startActivity(cartIntent)
    }
}


