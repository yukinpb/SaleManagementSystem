package com.example.fe.model

import com.example.fe.model.dto.InstallmentContractDto
import java.io.Serializable
import java.sql.Date

data class InstallmentContract(
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
    var customer: Customer? = null,
    var lendingPartner: LendingPartner? = null,
    var user: User? = null,
    var collaterals: List<InstallmentCollateral> = mutableListOf(),
    var products: List<InstallmentProduct> = mutableListOf(),
): Serializable

fun InstallmentContract.toDto(): InstallmentContractDto {
    return InstallmentContractDto(
        id = this.id,
        amountRemaining = this.amountRemaining,
        annualInterestRate = this.annualInterestRate,
        interestRateIncrease = this.interestRateIncrease,
        penaltyRate = this.penaltyRate,
        createDate = this.createDate,
        firstDatePaid = this.firstDatePaid,
        installmentPeriod = this.installmentPeriod,
        dayPaidEachMonth = this.dayPaidEachMonth,
        maximumDayOutstanding = this.maximumDayOutstanding,
        status = this.status,
        customerId = this.customer?.id ?: 0,
        lendingPartnerId = this.lendingPartner?.id ?: 0,
        userId = this.user?.id ?: 0,
        collaterals = this.collaterals.map { it.toDto() },
        products = this.products.map { it.toDto() }
    )
}