package com.example.hasiruusiru

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup Map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Button Listeners
        val btnAddTree = findViewById<Button>(R.id.btnAddTree)
        val btnOxygenScore = findViewById<Button>(R.id.btnOxygenScore)
        val btnSpeciesGuide = findViewById<Button>(R.id.btnSpeciesGuide)

        btnAddTree.setOnClickListener {
            startActivity(Intent(this, AddTreeActivity::class.java))
        }

        btnOxygenScore.setOnClickListener {
            startActivity(Intent(this, OxygenScoreActivity::class.java))
        }

        btnSpeciesGuide.setOnClickListener {
            startActivity(Intent(this, SpeciesGuideActivity::class.java))
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Default location Bengaluru
        val bengaluru = LatLng(12.9716, 77.5946)
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(bengaluru, 12f)
        )

        // Sample tree marker
        googleMap.addMarker(
            MarkerOptions()
                .position(bengaluru)
                .title("Sample Tree Neem")
        )
    }
}