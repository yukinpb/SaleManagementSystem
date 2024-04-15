package com.example.fe.retrofit

import com.example.fe.model.Customer
import com.example.fe.utilities.SqlDateDeserializer
import com.example.fe.utilities.SqlDateSerializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.sql.Date

interface CustomerApiService {
    @GET("search")
    suspend fun getCustomer(@Query("name") name: String): List<Customer>

    @POST("add")
    suspend fun addCustomer(@Body customer: Customer): Customer?

    @PUT("edit")
    suspend fun editCustomer(@Body customer: Customer): Customer?

    @DELETE("delete/{id}")
    suspend fun deleteCustomer(@Path("id") id: Int)

    @POST("sort-by-revenue")
    @JvmSuppressWildcards
    suspend fun sortByRevenue(@Body customers: List<Customer>): List<Customer>

    @POST("sort-by-remaining")
    @JvmSuppressWildcards
    suspend fun sortByRemaining(@Body customers: List<Customer>): List<Customer>

    @GET("all-by-name")
    suspend fun getAllCustomerByName(): List<Customer>

    @POST("revenue")
    suspend fun calculateRevenue(@Body customer: Customer): Double

    @POST("amount-remaining")
    suspend fun calculateRemaining(@Body customer: Customer): Double

    @POST("contract")
    suspend fun calculateNumberContract(@Body customer: Customer): Int

}

object CustomerApi {
    val retrofitService: CustomerApiService by lazy {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, SqlDateDeserializer())
            .registerTypeAdapter(Date::class.java, SqlDateSerializer())
            .setPrettyPrinting()
            .create()
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://192.168.56.1:8080/api/customer/")
            .build().create(CustomerApiService::class.java)
    }
}