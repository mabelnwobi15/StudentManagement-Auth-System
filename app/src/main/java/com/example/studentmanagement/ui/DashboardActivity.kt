package com.example.studentmanagement.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentmanagement.R
import com.example.studentmanagement.api.ApiService
import com.example.studentmanagement.utils.SessionManager
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sessionManager = SessionManager(this)
        apiService = ApiService.create()

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val adminPanelLayout = findViewById<View>(R.id.adminPanelLayout)

        val role = sessionManager.fetchUserRole()
        tvWelcome.text = "Welcome! Role: $role"

        // Role-Based Access Control (RBAC)
        if (role == "ADMIN") {
            adminPanelLayout.visibility = View.VISIBLE
            loadStudentDataForAdmin()
        } else {
            adminPanelLayout.visibility = View.GONE
            // Load student-specific profile view
        }

        // Secure Logout Implementation
        btnLogout.setOnClickListener {
            sessionManager.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun loadStudentDataForAdmin() {
        val token = "Bearer ${sessionManager.fetchAuthToken()}"
        lifecycleScope.launch {
            try {
                val response = apiService.getStudents(token)
                if (response.isSuccessful && response.body() != null) {
                    val students = response.body()!!
                    // Bind students list to RecyclerView or Adapter here
                    Toast.makeText(applicationContext, "Loaded ${students.size} students successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Failed to fetch student data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
