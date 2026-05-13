package com.example.hasiruusiru

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sharedPreferences = getSharedPreferences(
            "HasiruUsiru", MODE_PRIVATE)

        val etFullName = findViewById<EditText>(
            R.id.etFullName)
        val etEmail = findViewById<EditText>(
            R.id.etRegEmail)
        val etPassword = findViewById<EditText>(
            R.id.etRegPassword)
        val etConfirm = findViewById<EditText>(
            R.id.etConfirmPassword)
        val btnRegister = findViewById<Button>(
            R.id.btnRegister)
        val tvBack = findViewById<TextView>(
            R.id.tvBack)
        val tvLogin = findViewById<TextView>(
            R.id.tvLoginLink)

        // Back Button
        tvBack.setOnClickListener {
            finish()
        }

        // Login Link
        tvLogin.setOnClickListener {
            finish()
        }

        // Register Button
        btnRegister.setOnClickListener {
            val name = etFullName.text
                .toString().trim()
            val email = etEmail.text
                .toString().trim()
            val password = etPassword.text
                .toString().trim()
            val confirm = etConfirm.text
                .toString().trim()

            when {
                name.isEmpty() -> {
                    Toast.makeText(this,
                        "Enter your name!",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                email.isEmpty() -> {
                    Toast.makeText(this,
                        "Enter your email!",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                password.length < 4 -> {
                    Toast.makeText(this,
                        "Password needs 4+ chars!",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                password != confirm -> {
                    Toast.makeText(this,
                        "Passwords do not match!",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Save user
            sharedPreferences.edit()
                .putBoolean("isLoggedIn", true)
                .putString("username", name)
                .putString("email", email)
                .apply()

            Toast.makeText(this,
                "Welcome $name!",
                Toast.LENGTH_LONG).show()

            val intent = Intent(
                this,
                HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}