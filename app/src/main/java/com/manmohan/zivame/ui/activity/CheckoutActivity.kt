package com.manmohan.zivame.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.manmohan.zivame.R
import com.manmohan.zivame.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {

    private lateinit var checkoutBinding: ActivityCheckoutBinding
    private val IN_PROGRESS = "Placing Order"
    private val ALMOST_DONE = "Almost Done"
    private val ORDER_PLACED = "Yay ! Order Placed"
    private var isBackEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_checkout)
        startOrderTimer()
    }


    /**
     * Method to start Order Placing API
     */
    private fun startOrderTimer() {
        object : CountDownTimer(30000, 1000) {
            override fun onFinish() {
                checkoutBinding.progressText = ORDER_PLACED
                checkoutBinding.progressBar.visibility = View.GONE
                checkoutBinding.compleOrderIcon.visibility = View.VISIBLE
                isBackEnabled = true
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.e("###", millisUntilFinished.toString())
                if (millisUntilFinished >= 16000) {
                    Log.e("###", "1")
                    checkoutBinding.progressText = IN_PROGRESS
                } else {
                    Log.e("###", "2")
                    checkoutBinding.progressText = ALMOST_DONE
                }
            }
        }.start()
    }


    override fun onBackPressed() {
        if (isBackEnabled) {
            super.onBackPressed()
            finish()
        }
    }
}