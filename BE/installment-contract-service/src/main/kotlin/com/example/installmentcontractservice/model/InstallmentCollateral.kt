package com.example.installmentcontractservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.sql.Date

@Entity
data class InstallmentCollateral(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var installmentDate: Date = Date(0),
    var value: Double = 0.0,
    var collateralId: Int = 0,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    var contract: InstallmentContract? = null,
)