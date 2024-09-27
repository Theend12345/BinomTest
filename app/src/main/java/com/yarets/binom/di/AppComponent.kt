package com.yarets.binom.di

import com.yarets.binom.app.ui.MapFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {
    fun injectMapFragment(mapFragment: MapFragment)
}