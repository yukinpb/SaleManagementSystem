package com.example.fe.retrofit

import com.example.fe.model.dto.InstallmentContractDto
import com.example.fe.utilities.SqlDateDeserializer
import com.example.fe.utilities.SqlDateSerializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.sql.Date

interface InstallmentContractApiService {
    @POST("add")
    suspend fun addInstallmentContract(@Body installmentContract: InstallmentContractDto): InstallmentContractDto

    @GET("search/{customerId}")
    suspend fun getInstallmentContractByCustomer(@Path("customerId") customerId: Int): List<InstallmentContractDto>
}

object InstallmentContractApi {
    val retrofitService: InstallmentContractApiService by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, SqlDateDeserializer())
            .registerTypeAdapter(Date::class.java, SqlDateSerializer())
            .setPrettyPrinting()
            .create()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://192.168.56.1:8080/api/installment-contract/")
            .build().create(InstallmentContractApiService::class.java)
    }
}