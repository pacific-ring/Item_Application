package com.manmohan.zivame.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.manmohan.zivame.R
import com.manmohan.zivame.databinding.ActivityCartBinding
import com.manmohan.zivame.model.Item
import com.manmohan.zivame.ui.adapters.CartProductAdapter

class CartActivity : AppCompatActivity() {

    private var selectedItemList : ArrayList<Item> = arrayListOf()
    private lateinit var cartProductAdapter: CartProductAdapter

    private lateinit var cartBinding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        selectedItemList = intent.getSerializableExtra("productList") as ArrayList<Item>
        displayItemInRecyclerView()
        initialiseView()
        setListener()
    }

    private fun initialiseView() {
        cartBinding.totalAmount = "Rs ${cartProductAdapter.getTotalAmount()}"
    }

    private fun setListener() {
        cartBinding.backButton.setOnClickListener {
            finish()
        }

        cartBinding.checkoutButton.setOnClickListener {
            //TODO : Add Order To DB (Using Dagger)
            //TODO : Open Checkout Screen
        }
    }

    private fun displayItemInRecyclerView() {
        cartProductAdapter = CartProductAdapter(selectedItemList, object : CartProductAdapter.CartItemAction{
            override fun onAmountModified(amount: Int) {
                cartBinding.totalAmount = "Rs $amount"
            }
        })

        cartBinding.selectedItemRv.apply {
            this.layoutManager = LinearLayoutManager(this@CartActivity)
            this.itemAnimator = DefaultItemAnimator()
            this.adapter = cartProductAdapter
        }
    }
}