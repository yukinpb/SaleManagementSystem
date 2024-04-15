package com.example.customerservice.model

import jakarta.persistence.Entity

@Entity(name = "customer")
data class Customer(
        var job: String = "",
        var workPlace: String = "",
        var bankCardNumber: String = "",
        var income: Double = 0.0
): Person()