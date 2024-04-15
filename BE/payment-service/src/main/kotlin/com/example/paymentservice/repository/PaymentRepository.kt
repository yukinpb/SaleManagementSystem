package com.example.paymentservice.repository

import com.example.paymentservice.model.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository: JpaRepository<Payment, Int> {
    fun findByContractId(contractId: Int): List<Payment>
}