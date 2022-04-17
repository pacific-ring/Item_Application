package com.manmohan.zivame.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Orders(
    val orderItem : String,
    @PrimaryKey(autoGenerate = false)
    val orderDate : Long,
    val totalSum : Int)