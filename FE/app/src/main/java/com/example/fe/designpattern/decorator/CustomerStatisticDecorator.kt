package com.example.fe.designpattern.decorator

import com.example.fe.model.Customer

open class CustomerStatisticDecorator(
    private val customerStatistic: CustomerStatistic
): CustomerStatistic {
    override suspend fun statisticCustomer(): List<Customer> {
        return customerStatistic.statisticCustomer()
    }
}