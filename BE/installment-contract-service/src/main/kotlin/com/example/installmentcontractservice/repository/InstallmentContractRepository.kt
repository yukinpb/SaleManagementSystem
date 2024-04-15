package com.example.installmentcontractservice.repository

import com.example.installmentcontractservice.model.InstallmentContract
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstallmentContractRepository: JpaRepository<InstallmentContract, Int> {
    fun findByCustomerId(customerId: Int): List<InstallmentContract>
}