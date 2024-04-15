package com.example.lendingpartnerservice.repository

import com.example.lendingpartnerservice.model.LendingPartner
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LendingPartnerRepository: JpaRepository<LendingPartner, Int> {
    fun findByNameContainingOrCompanyNameContainingAndMaximumLoanGreaterThanEqualOrderByAnnualInterestRateAsc(
        name: String,
        companyName: String,
        maximumLoan: Double
    ): List<LendingPartner>
    fun findByMaximumLoanGreaterThanEqualOrderByAnnualInterestRateAsc(loanAmount: Double): List<LendingPartner>
}