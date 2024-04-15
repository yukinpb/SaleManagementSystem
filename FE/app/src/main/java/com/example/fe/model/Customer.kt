package com.example.fe.model

import com.example.fe.retrofit.CustomerApi

open class Customer(
        var job: String = "",
        var workPlace: String = "",
        var bankCardNumber: String = "",
        var income: Double = 0.0
): Person()

suspend fun Customer.calculate(): StatisticCustomer {
        return StatisticCustomer(
                this,
                CustomerApi.retrofitService.calculateRevenue(this),
                CustomerApi.retrofitService.calculateNumberContract(this),
                CustomerApi.retrofitService.calculateRemaining(this)
        )
}