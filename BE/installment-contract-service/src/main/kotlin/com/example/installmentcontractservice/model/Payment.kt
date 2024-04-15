package com.example.installmentcontractservice.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

data class Payment (
    val id: Int = 0,
    var paymentMethod: String = "",
    var paymentDue: Date? = Date(),
    var paymentDate: Date = Date(),
    var amountDue: Double = 0.0,
    var amountPenalty: Double = 0.0,
    var amountPaid: Double = 0.0,
    var status: String = "",
    var contractId: Int = 0,
    var receiverId: Int = 0
)