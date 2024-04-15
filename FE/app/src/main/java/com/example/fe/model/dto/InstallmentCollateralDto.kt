package com.example.fe.model.dto

import java.sql.Date


data class InstallmentCollateralDto (
    var id: Int = 0,
    var installmentDate: Date = Date(0),
    var value: Double = 0.0,
    var collateralId: Int = 0
)