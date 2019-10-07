package com.e.moal_admin

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import net.daum.mf.map.api.MapView

class StoreRegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val mapView = MapView(this)

        mapView.setDaumMapApiKey("e4b214a56c02f90f1c751c065913ed36")

        val mapViewContainer = map_view

        mapViewContainer.addView(mapView)

        window.statusBarColor = Color.parseColor("#344955")
    }
}