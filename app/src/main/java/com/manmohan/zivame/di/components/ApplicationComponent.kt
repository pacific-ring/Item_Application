package com.manmohan.zivame.di.components

import android.content.Context
import com.manmohan.zivame.di.modules.DatabaseModule
import com.manmohan.zivame.di.modules.NetworkModule
import com.manmohan.zivame.ui.activity.CartActivity
import com.manmohan.zivame.ui.activity.HomeActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules =[NetworkModule :: class, DatabaseModule :: class])
interface ApplicationComponent {

    fun inject(activity : HomeActivity)

    fun injectCart(activity: CartActivity)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }
}