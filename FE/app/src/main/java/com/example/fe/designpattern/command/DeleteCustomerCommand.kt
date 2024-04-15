package com.example.fe.designpattern.command

import com.example.fe.model.Customer
import com.example.fe.retrofit.CustomerApi
import com.example.fe.retrofit.ProductApi

class DeleteCustomerCommand(
    private val customer: Customer
): Command {
    override suspend fun execute() {
        CustomerApi.retrofitService.deleteCustomer(customer.id)
    }

    override suspend fun undo() {
        CustomerApi.retrofitService.addCustomer(customer)
    }
}