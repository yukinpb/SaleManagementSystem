package com.example.fe.model

import java.io.Serializable
import java.sql.Date

data class Payment(
    val id: Int = 0,
    var paymentMethod: String = "",
    var paymentDue: Date = Date(0),
    var paymentDate: Date = Date(0),
    var amountDue: Double = 0.0,
    var amountPenalty: Double = 0.0,
    var amountPaid: Double = 0.0,
    var status: String = "",
    var contractId: Int = 0,
    var receiverId: Int = 0
): Serializable