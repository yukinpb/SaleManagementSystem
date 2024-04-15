package com.example.installmentcontractservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.sql.Date

data class InstallmentCollateral(
    var id: Int = 0,
    var installmentDate: Date = Date(0),
    var value: Double = 0.0,
    var collateralId: Int = 0,
    @JsonIgnore
    var contract: InstallmentContract? = null
)