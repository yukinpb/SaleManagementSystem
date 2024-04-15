package com.example.fe.model.dto

import com.example.fe.model.InstallmentContract

data class InstallmentProductDto(
    var id: Int = 0,
    var quantity: Double = 0.0,
    var amount: Double = 0.0,
    var productId: Int = 0
)