package com.example.hasiruusiru

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        try {
            sharedPreferences = getSharedPreferences(
                "HasiruUsiru", MODE_PRIVATE)

            // Get username
            val username = sharedPreferences
                .getString("username", "User")
            findViewById<TextView>(R.id.tvWelcome)
                .text = "Welcome, $username!"

            // Load stats safely
            loadStats()

            // Buttons
            findViewById<Button>(R.id.btnHomeMap)
                .setOnClickListener {
                    startActivity(Intent(this,
                        MainActivity::class.java))
                }

            findViewById<Button>(R.id.btnHomeAddTree)
                .setOnClickListener {
                    startActivity(Intent(this,
                        AddTreeActivity::class.java))
                }

            findViewById<Button>(R.id.btnHomeOxygen)
                .setOnClickListener {
                    startActivity(Intent(this,
                        OxygenScoreActivity::class.java))
                }

            findViewById<Button>(R.id.btnHomeSpecies)
                .setOnClickListener {
                    startActivity(Intent(this,
                        SpeciesGuideActivity::class.java))
                }

            // Logout
            findViewById<Button>(R.id.btnLogout)
                .setOnClickListener {
                    sharedPreferences.edit()
                        .clear().apply()
                    val intent = Intent(this,
                        LoginActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadStats() {
        try {
            val db = AppDatabase.getInstance(this)
            val treeCount = db.treeDao()
                .getTreeCount()
            val emptyPits = db.treeDao()
                .getEmptyPitCount()
            val totalGirth = db.treeDao()
                .getTotalGirth()
            val o2Score = (totalGirth * 1.5f).toInt()

            findViewById<TextView>(
                R.id.tvHomeTreeCount)
                .text = treeCount.toString()
            findViewById<TextView>(
                R.id.tvHomeO2Score)
                .text = o2Score.toString()
            findViewById<TextView>(
                R.id.tvHomeEmptyPits)
                .text = emptyPits.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        loadStats()
    }
}