package com.example.fe.retrofit

import com.example.fe.model.LendingPartner
import com.example.fe.utilities.SqlDateDeserializer
import com.example.fe.utilities.SqlDateSerializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.sql.Date

interface LendingPartnerApiService {
    @GET("search/loan-amount/{loanAmount}")
    suspend fun getAllLendingPartnersWithLoanAmount(
        @Path("loanAmount") loanAmount: Double
    ): List<LendingPartner>

    @GET("search")
    suspend fun searchLendingPartners(@Query("name") name: String, @Query("loanAmount") loanAmount: Double): List<LendingPartner>
}

object LendingPartnerApi {
    val retrofitService: LendingPartnerApiService by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, SqlDateDeserializer())
            .registerTypeAdapter(Date::class.java, SqlDateSerializer())
            .setPrettyPrinting()
            .create()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://192.168.56.1:8080/api/lending-partner/")
            .build().create(LendingPartnerApiService::class.java)
    }
}