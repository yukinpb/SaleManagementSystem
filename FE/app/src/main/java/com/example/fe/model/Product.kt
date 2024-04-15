package com.example.fe.model

import java.io.Serializable

data class Product(
        val id: Int = 0,
        var name: String = "",
        var price: Double = 0.0,
        var des: String = "",
        var remaining: Double = 0.0
): Serializable

