package com.manmohan.zivame.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import com.manmohan.zivame.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.*
import javax.inject.Inject

class CartActivity : AppCompatActivity() {

    private var selectedItemList : ArrayList<Item> = arrayListOf()
    private lateinit var cartProductAdapter: CartProductAdapter
    private lateinit var cartBinding: ActivityCartBinding

    @Inject
    lateinit var orderDb: OrderDb


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        selectedItemList = intent.getSerializableExtra(Constants.PRODUCT_KEY)
                as ArrayList<Item>

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

    /**
     * Displaying the items in recycler view
     */
    private fun displayItemInRecyclerView() {
        cartProductAdapter = CartProductAdapter(selectedItemList,
            object : CartProductAdapter.CartItemAction{
            override fun onAmountModified(amount: Int) {
                cartBinding.totalAmount = "Rs $amount"
            }
        })

        cartBinding.selectedItemRv.apply {
            this.layoutManager = LinearLayoutManager(this@CartActivity)
            this.itemAnimator = DefaultItemAnimator()
            if(!selectedItemList.isNullOrEmpty()){
                cartBinding.footer.visibility = View.VISIBLE
                this.visibility = View.VISIBLE
                cartBinding.emptyCartContainer.visibility = View.GONE
            }
            this.adapter = cartProductAdapter
        }
    }

    /**
     * Initialising the amount of the cart
     */
    private fun initialiseView() {
        cartBinding.totalAmount = "Rs ${cartProductAdapter.getTotalAmount()}"
    }

    /**
     * Setting the listeners
     */
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

    /**
     * Saving the order scema to DB
     */
    private fun saveItemToDB() {
        CoroutineScope(Dispatchers.IO).launch {
            orderDb.getRoomDao().addPlacedOrders(Orders(selectedItemList.toString(),
                System.currentTimeMillis(),
                Integer.parseInt("1452")
            ))
        }
    }


    /**
     * Opening Checkout Activity
     */
    private fun openCheckOutActivity() {
        selectedItemList = arrayListOf()
        startActivity(Intent(this,CheckoutActivity ::class.java ))
        finish()
    }
}