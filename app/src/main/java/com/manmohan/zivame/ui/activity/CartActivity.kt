package com.manmohan.zivame.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.manmohan.zivame.R
import com.manmohan.zivame.ZivameApplication
import com.manmohan.zivame.databinding.ActivityCartBinding
import com.manmohan.zivame.db.OrderDb
import com.manmohan.zivame.model.Item
import com.manmohan.zivame.model.Orders
import com.manmohan.zivame.ui.adapters.CartProductAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.*
import javax.inject.Inject

class CartActivity : AppCompatActivity() {

    private var selectedItemList : ArrayList<Item> = arrayListOf()
    private lateinit var cartProductAdapter: CartProductAdapter

    @Inject
    lateinit var orderDb: OrderDb

    private lateinit var cartBinding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        selectedItemList = intent.getSerializableExtra("productList") as ArrayList<Item>
        initialize()
        displayItemInRecyclerView()
        initialiseView()
        setListener()
    }

    /**
     * Method to initialise DaggerComponent
     */
    private fun initialize() {
        (application as ZivameApplication).applicationComponent.injectCart(this)
    }

    private fun initialiseView() {
        cartBinding.totalAmount = "Rs ${cartProductAdapter.getTotalAmount()}"
    }

    private fun setListener() {
        cartBinding.backButton.setOnClickListener {
            finish()
        }

        cartBinding.checkoutButton.setOnClickListener {
            if (selectedItemList.isNullOrEmpty()){
                Toast.makeText(this,
                    "No Item Present In Cart",
                    Toast.LENGTH_SHORT).show()
            }else{
                saveItemToDB()
                openCheckOutActivity()
            }
        }
    }

    private fun openCheckOutActivity() {
        Log.e("MMG", "HUSHS")
    }

    private fun saveItemToDB() {
        Log.e("MMG","This is called")
        CoroutineScope(Dispatchers.IO).launch {
            orderDb.getRoomDao().addPlacedOrders(Orders(selectedItemList.toString(),
                System.currentTimeMillis(),
                Integer.parseInt("1452")
            ))
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