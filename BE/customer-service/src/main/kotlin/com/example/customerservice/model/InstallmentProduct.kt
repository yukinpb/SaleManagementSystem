package com.example.installmentcontractservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

data class InstallmentProduct(
    var id: Int = 0,
    var quantity: Double = 0.0,
    var amount: Double = 0.0,
    var productId: Int = 0,
    @JsonIgnore
    var contract: InstallmentContract? = null
)