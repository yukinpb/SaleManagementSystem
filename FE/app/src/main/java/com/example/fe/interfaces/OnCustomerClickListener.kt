package com.example.fe.interfaces

import com.example.fe.model.Customer

interface OnCustomerClickListener {
    fun onItemClick(customer: Customer)
}