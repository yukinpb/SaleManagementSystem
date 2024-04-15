package com.example.collateralservice.model

import jakarta.persistence.*

@Entity
data class Collateral(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,
        var type: String = "",
        var name: String = "",
        var value: Double = 0.0,
        var status: String = "",
        @Column(name = "`condition`")
        var condition: String = "",
        var customerId: Int = 0
)
