package com.example.fe.retrofit

import com.example.fe.model.User
import com.example.fe.utilities.SqlDateDeserializer
import com.example.fe.utilities.SqlDateSerializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Date

interface UserApiService {
    @GET("login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): User?
}

object UserApi {
    val retrofitService: UserApiService by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, SqlDateDeserializer())
            .registerTypeAdapter(Date::class.java, SqlDateSerializer())
            .setPrettyPrinting()
            .create()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://192.168.56.1:8080/api/user/")
            .build()
            .create(UserApiService::class.java)
    }
}