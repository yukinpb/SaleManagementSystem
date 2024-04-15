package com.example.fe.designpattern.decorator

import com.example.fe.model.Customer
import com.example.fe.retrofit.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CustomerStatisticByName: CustomerStatistic {
    override suspend fun statisticCustomer(): List<Customer> {
        return CoroutineScope(Dispatchers.IO).async {
            CustomerApi.retrofitService.getAllCustomerByName()
        }.await()
    }
}