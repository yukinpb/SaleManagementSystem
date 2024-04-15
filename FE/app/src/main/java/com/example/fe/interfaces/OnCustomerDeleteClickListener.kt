package com.example.fe.interfaces

import com.example.fe.model.Customer

interface OnCustomerDeleteClickListener {
    fun onDeleteClick(customer: Customer)
}