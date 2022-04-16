package com.manmohan.zivame.di.components

import com.manmohan.zivame.ui.activity.HomeActivity
import com.manmohan.zivame.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =[NetworkModule :: class])
interface HomeScreenActivityComponent {

    fun inject(activity : HomeActivity)

}