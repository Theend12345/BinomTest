package com.yarets.binom.app.model

import org.osmdroid.util.GeoPoint

data class Person(val id: Int, val name: String, val image: Int, val gpsX: Double, val gpsY: Double){
    fun getGeoPosition(): GeoPoint = GeoPoint(gpsY, gpsX)
}
