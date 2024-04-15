package com.example.fe.model

import com.example.fe.retrofit.CustomerApi

class StatisticCustomer (
    var customer: Customer,
    var revenue: Double = 0.0,
    var numberOfContract: Int = 0,
    var remaining: Double = 0.0
)

