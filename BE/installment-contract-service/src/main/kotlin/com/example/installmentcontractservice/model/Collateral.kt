package com.example.installmentcontractservice.model

import jakarta.persistence.*

data class Collateral(
        val id: Int = 0,
        var type: String = "",
        var name: String = "",
        var value: Double = 0.0,
        var status: String = "",
        var condition: String = "",
        var customerId: Int = 0
)
