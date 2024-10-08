package com.yarets.binom.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yarets.binom.R
import com.yarets.binom.app.ui.MapFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.container, MapFragment()).commit()
    }
}