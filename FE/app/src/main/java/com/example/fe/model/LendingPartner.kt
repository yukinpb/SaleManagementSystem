package com.example.fe.model

data class LendingPartner(
    var companyName: String = "",
    var companyAddress: String = "",
    var companyPhoneNumber: String = "",
    var companyEmail: String = "",
    var maximumLoan: Double = 0.0,
    var annualInterestRate: Double = 0.0,
    var interestRateIncrease: Double = 0.0
): Person()
