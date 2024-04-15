package com.example.installmentcontractservice.model

import jakarta.persistence.*
import java.util.Date

@Entity
data class InstallmentContract(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    var amountRemaining: Double = 0.0,
    var annualInterestRate: Double = 0.0,
    var interestRateIncrease: Double = 0.0,
    var penaltyRate: Double = 0.0,
    var createDate: Date = Date(),
    var firstDatePaid: Date = Date(),
    var installmentPeriod: Int = 0,
    var dayPaidEachMonth: String = "",
    var maximumDayOutstanding: Int = 0,
    var status: String = "",
    var customerId: Int = 0,
    var lendingPartnerId: Int = 0,
    var userId: Int = 0,
    @OneToMany(mappedBy = "contract", cascade = [CascadeType.ALL])
    var collaterals: List<InstallmentCollateral> = mutableListOf(),
    @OneToMany(mappedBy = "contract", cascade = [CascadeType.ALL])
    var products: List<InstallmentProduct> = mutableListOf()
)
