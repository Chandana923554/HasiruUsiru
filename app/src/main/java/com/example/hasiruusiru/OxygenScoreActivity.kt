package com.example.hasiruusiru

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OxygenScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oxygen_score)

        // Find Views
        val tvScore = findViewById<TextView>(R.id.tvOxygenScore)
        val tvTreeCount = findViewById<TextView>(R.id.tvTreeCount)
        val tvEmptyPits = findViewById<TextView>(R.id.tvEmptyPits)
        val tvTopSpecies = findViewById<TextView>(R.id.tvTopSpecies)
        val progressBar = findViewById<ProgressBar>(R.id.progressOxygen)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Set Sample Values
        tvScore.text = "247.5"
        tvTreeCount.text = "12"
        tvEmptyPits.text = "3"
        tvTopSpecies.text = "Peepal"
        progressBar.progress = 75

        // Back Button
        btnBack.setOnClickListener {
            finish()
        }
    }
}