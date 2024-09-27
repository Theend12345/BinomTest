package com.yarets.binom.app

import android.app.Application
import com.yarets.binom.di.AppComponent
import com.yarets.binom.di.DaggerAppComponent

class App : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}