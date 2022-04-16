package com.manmohan.zivame.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.manmohan.zivame.R
import com.manmohan.zivame.model.Item
import com.manmohan.zivame.model.Products

class CartActivity : AppCompatActivity() {

    private lateinit var selectedItemList : List<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        selectedItemList = intent.getSerializableExtra("productList") as ArrayList<Item>
    }
}