package com.manmohan.zivame

import android.app.Application
import com.manmohan.zivame.di.components.ApplicationComponent
import com.manmohan.zivame.di.components.DaggerApplicationComponent

class ZivameApplication : Application() {

    lateinit var applicationComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}