package com.example.fe.retrofit

import com.example.fe.model.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("search")
    suspend fun getProduct(@Query("name") name: String): List<Product>

    @POST("add")
    suspend fun addProduct(@Body product: Product): Product?

    @PUT("edit")
    suspend fun editProduct(@Body product: Product): Product?

    @DELETE("delete/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)

    @GET("{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}

object ProductApi {
    val retrofitService: ProductApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.56.1:8080/api/product/")
            .build().create(ProductApiService::class.java)
    }
}