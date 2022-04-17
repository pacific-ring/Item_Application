package com.manmohan.zivame.db

import androidx.room.Dao
import androidx.room.Insert
import com.manmohan.zivame.model.Orders

@Dao
interface RoomDao {

    @Insert
    suspend  fun addPlacedOrders(orders: Orders)
}