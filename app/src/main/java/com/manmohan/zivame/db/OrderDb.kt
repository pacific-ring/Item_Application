package com.manmohan.zivame.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manmohan.zivame.model.Orders

@Database(entities = [Orders :: class], version = 1)
abstract  class OrderDb : RoomDatabase() {
    abstract fun getRoomDao() : RoomDao
}