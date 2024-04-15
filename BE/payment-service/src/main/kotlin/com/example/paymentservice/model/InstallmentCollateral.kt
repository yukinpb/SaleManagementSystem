package com.example.installmentcontractservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

data class InstallmentCollateral(
    var id: Int = 0,
    var quantity: Double = 0.0,
    var totalValue: Double = 0.0,
    var contract: InstallmentContract? = null
)