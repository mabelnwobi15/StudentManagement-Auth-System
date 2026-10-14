package com.example.studentmanagement.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentmanagement.R
import com.example.studentmanagement.api.ApiService
import com.example.studentmanagement.model.LoginRequest
import com.example.studentmanagement.utils.SessionManager
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        // Persistent Session check
        if (sessionManager.fetchAuthToken() != null) {
            navigateToDashboard()
            return
        }

        setContentView(R.layout.activity_login)

        apiService = ApiService.create()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Basic Input Validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performLogin(email, password)
        }
    }

    private fun performLogin(email: String, pass: String) {
        val request = LoginRequest(email, pass)

        lifecycleScope.launch {
            try {
                val response = apiService.login(request)
                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!

                    // Save authenticated session token and role securely
                    sessionManager.saveSession(authResponse.token, authResponse.role)

                    Toast.makeText(applicationContext, "Login Successful!", Toast.LENGTH_SHORT).show()
                    navigateToDashboard()
                } else {
                    Toast.makeText(applicationContext, "Invalid credentials or server error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Network Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        // Clear back stack so user cannot go back to login screen using the back button
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
