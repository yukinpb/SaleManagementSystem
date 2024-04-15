package com.example.collateralservice.service

import com.example.collateralservice.model.Collateral
import com.example.collateralservice.repository.CollateralRepository
import org.springframework.stereotype.Service

enum class CollateralStatus {
    ACTIVE,
    INACTIVE
}

@Service
class CollateralService(
        private val collateralRepository: CollateralRepository
) {
    fun getCollateralById(collateralId: Int): Collateral? {
        return collateralRepository.findById(collateralId).get()
    }

    fun addCollateral(collateral: Collateral): Collateral? {
        if(!checkInfoValid(collateral)) {
            return null
        }
        collateral.status = CollateralStatus.ACTIVE.toString()
        return collateralRepository.save(collateral)
    }

    private fun checkInfoValid(collateral: Collateral): Boolean {
        return collateral.name.isNotEmpty() && collateral.value > 0
    }

    fun searchCollateralByCustomer(customerId: Int): List<Collateral> {
        return collateralRepository.findByCustomerId(customerId)
    }

    fun searchCollateralByCustomerAndName(customerId: Int, name: String): List<Collateral> {
        return collateralRepository.findByCustomerIdAndNameContainingAndStatus(customerId, name, CollateralStatus.ACTIVE.toString())
    }

    fun editCollateral(collateral: Collateral): Collateral? {
        if(!checkInfoValid(collateral)) {
            return null
        }
        return collateralRepository.save(collateral)
    }

    fun deleteCollateral(id: Int) {
        collateralRepository.deleteById(id)
    }

    fun checkCollateralsValue(collaterals: List<Collateral>, loanAmount: Double): Boolean {
        return collaterals.sumOf { it.value } >= loanAmount
    }
}