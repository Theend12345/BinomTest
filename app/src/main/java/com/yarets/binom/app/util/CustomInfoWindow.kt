package com.yarets.binom.app.util

import android.widget.TextView
import com.yarets.binom.R
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow

class CustomInfoWindow(
    mapView: MapView?
) : InfoWindow(R.layout.info_window, mapView) {

    override fun onOpen(item: Any?) {
        this.mView.findViewById<TextView>(R.id.title).text = (item as Marker).title
        this.mView.findViewById<TextView>(R.id.description).text = (item as Marker).snippet
        this.mView.y += 320
        this.mView.x += 180
    }

    override fun onClose() {}
}