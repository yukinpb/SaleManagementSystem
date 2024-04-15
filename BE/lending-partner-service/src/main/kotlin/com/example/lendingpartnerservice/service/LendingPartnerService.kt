package com.example.lendingpartnerservice.service

import com.example.lendingpartnerservice.model.LendingPartner
import com.example.lendingpartnerservice.repository.LendingPartnerRepository
import org.springframework.stereotype.Service

@Service
class LendingPartnerService(
        private val lendingPartnerRepository: LendingPartnerRepository
) {
    fun getLendingPartnerById(id: Int): LendingPartner? = lendingPartnerRepository.findById(id).get()

    fun searchLendingPartner(name: String, loanAmount: Double): List<LendingPartner> = lendingPartnerRepository.findByNameContainingOrCompanyNameContainingAndMaximumLoanGreaterThanEqualOrderByAnnualInterestRateAsc(name, name, loanAmount)

    fun addLendingPartner(lendingPartner: LendingPartner): LendingPartner? {
        if(!checkInfoValid(lendingPartner)) {
            return null
        }
        return lendingPartnerRepository.save(lendingPartner)
    }

    private fun checkInfoValid(lendingPartner: LendingPartner): Boolean {
        return lendingPartner.companyName.isNotEmpty()
                && lendingPartner.companyPhoneNumber.isNotEmpty()
                && lendingPartner.companyAddress.isNotEmpty()
                && lendingPartner.companyEmail.isNotEmpty()
                && lendingPartner.maximumLoan != 0.0
    }

    fun editLendingPartner(lendingPartner: LendingPartner): LendingPartner? {
        if(!checkInfoValid(lendingPartner)) {
            return null
        }
        return lendingPartnerRepository.save(lendingPartner)
    }

    fun deleteLendingPartner(id: Int) {
        lendingPartnerRepository.deleteById(id)
    }

    fun getAllLendingPartnerWithLoanAmount(loanAmount: Double): List<LendingPartner> {
        return lendingPartnerRepository.findByMaximumLoanGreaterThanEqualOrderByAnnualInterestRateAsc(loanAmount)
    }

}