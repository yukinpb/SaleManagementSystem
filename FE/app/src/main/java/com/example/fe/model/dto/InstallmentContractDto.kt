package com.example.fe.model.dto

import java.sql.Date

data class InstallmentContractDto(
    var id: Int = 0,
    var amountRemaining: Double = 0.0,
    var annualInterestRate: Double = 0.0,
    var interestRateIncrease: Double = 0.0,
    var penaltyRate: Double = 0.0,
    var createDate: Date = Date(0),
    var firstDatePaid: Date = Date(0),
    var installmentPeriod: Int = 0,
    var dayPaidEachMonth: String = "",
    var maximumDayOutstanding: Int = 0,
    var status: String = "",
    var customerId: Int = 0,
    var lendingPartnerId: Int = 0,
    var userId: Int = 0,
    var collaterals: List<InstallmentCollateralDto> = mutableListOf(),
    var products: List<InstallmentProductDto> = mutableListOf(),
)