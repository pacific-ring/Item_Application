package com.manmohan.zivame.di.modules

import android.content.Context
import androidx.room.Room
import com.manmohan.zivame.db.OrderDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {


    @Singleton
    @Provides
    fun provideDBInstance(context: Context) : OrderDb{
        return Room.databaseBuilder(
            context,
            OrderDb::class.java,
            "OrdersDB"
        ).build()
    }
}