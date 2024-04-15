package com.example.collateralservice.repository

import com.example.collateralservice.model.Collateral
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CollateralRepository: JpaRepository<Collateral, Int> {
    fun findByCustomerId(customerId: Int): List<Collateral>
    fun findByCustomerIdAndNameContainingAndStatus(customerId: Int, name: String, status: String): List<Collateral>
}