package com.example.hasiruusiru

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SpeciesGuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_species_guide)

        // Back Button
        val btnBack = findViewById<Button>(R.id.btnBackFromSpecies)
        btnBack.setOnClickListener {
            finish()
        }
    }
}