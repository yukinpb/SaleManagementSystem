package com.example.installmentcontractservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class InstallmentProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var quantity: Double = 0.0,
    var amount: Double = 0.0,
    var productId: Int = 0,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    var contract: InstallmentContract? = null,
)