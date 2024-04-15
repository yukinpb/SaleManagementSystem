package com.example.fe.designpattern.decorator

import com.example.fe.model.Customer

interface CustomerStatistic {
    suspend fun statisticCustomer(): List<Customer>
}