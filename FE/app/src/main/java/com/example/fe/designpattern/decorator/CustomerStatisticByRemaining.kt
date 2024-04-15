package com.example.fe.designpattern.decorator

import com.example.fe.model.Customer
import com.example.fe.retrofit.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class CustomerStatisticByRemaining(
    customerStatistic: CustomerStatistic
): CustomerStatisticDecorator(customerStatistic) {
    override suspend fun statisticCustomer(): List<Customer> {
        val customers = super.statisticCustomer()
        return CoroutineScope(Dispatchers.IO).async {
            CustomerApi.retrofitService.sortByRemaining(customers)
        }.await()
    }
}