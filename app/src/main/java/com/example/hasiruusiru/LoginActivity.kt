package com.example.hasiruusiru

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences = getSharedPreferences(
            "HasiruUsiru", MODE_PRIVATE)

        val etUsername = findViewById<EditText>(
            R.id.etUsername)
        val etPassword = findViewById<EditText>(
            R.id.etPassword)
        val btnLogin = findViewById<Button>(
            R.id.btnLogin)
        val tvRegister = findViewById<TextView>(
            R.id.tvRegister)
        val btnGoogle = findViewById<LinearLayout>(
            R.id.btnGoogle)
        val btnFacebook = findViewById<LinearLayout>(
            R.id.btnFacebook)
        val tvForgot = findViewById<TextView>(
            R.id.tvForgotPassword)

        // Sign In Button
        btnLogin.setOnClickListener {
            val username = etUsername.text
                .toString().trim()
            val password = etPassword.text
                .toString().trim()

            if (username.isEmpty() ||
                password.isEmpty()) {
                Toast.makeText(this,
                    "Enter email and password!",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length >= 4) {
                sharedPreferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .putString("username", username)
                    .apply()

                Toast.makeText(this,
                    "Welcome $username!",
                    Toast.LENGTH_SHORT).show()

                val intent = Intent(
                    this,
                    HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this,
                    "Password needs 4+ characters!",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // Google Button — Opens Google
        btnGoogle.setOnClickListener {
            try {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://accounts.google.com"
                    )
                )
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this,
                    "Cannot open Google!",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // Facebook Button — Opens Facebook
        btnFacebook.setOnClickListener {
            try {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        "https://www.facebook.com"
                    )
                )
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this,
                    "Cannot open Facebook!",
                    Toast.LENGTH_SHORT).show()
            }
        }

        // Forgot Password
        tvForgot.setOnClickListener {
            Toast.makeText(this,
                "Reset link sent to your email!",
                Toast.LENGTH_LONG).show()
        }

        // Create Account — Opens Register Screen
        tvRegister.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }
}