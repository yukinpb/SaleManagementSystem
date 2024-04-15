package com.example.productservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,
        var name: String = "",
        var price: Double = 0.0,
        var des: String = "",
        var remaining: Double = 0.0
)
