package com.example.studentmanagement.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val role: String, // "ADMIN" or "STUDENT"
    val message: String
)

data class Student(
    val id: String,
    val name: String,
    val email: String,
    val course: String,
    val gpa: Double
)
