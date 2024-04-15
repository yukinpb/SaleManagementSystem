package com.example.fe.retrofit

import com.example.fe.model.Collateral
import com.example.fe.utilities.SqlDateDeserializer
import com.example.fe.utilities.SqlDateSerializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.sql.Date

interface CollateralApiService {
    @GET("search-by-name-and-customer")
    suspend fun searchCollaterals(@Query("name") name: String, @Query("customerId") customerId: Int): List<Collateral>

    @POST("add")
    suspend fun addCollateral(@Body collateral: Collateral): Collateral
}

object CollateralApi {
    val retrofitService: CollateralApiService by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, SqlDateDeserializer())
            .registerTypeAdapter(Date::class.java, SqlDateSerializer())
            .setPrettyPrinting()
            .create()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://192.168.56.1:8080/api/collateral/")
            .build().create(CollateralApiService::class.java)
    }
}
