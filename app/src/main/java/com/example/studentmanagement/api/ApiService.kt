package com.example.studentmanagement.api

import com.example.studentmanagement.model.AuthResponse
import com.example.studentmanagement.model.LoginRequest
import com.example.studentmanagement.model.Student
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("api/students")
    suspend fun getStudents(@Header("Authorization") token: String): Response<List<Student>>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:5257/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
