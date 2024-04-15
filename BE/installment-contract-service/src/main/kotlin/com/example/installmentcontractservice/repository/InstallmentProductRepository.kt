package com.example.installmentcontractservice.repository

import com.example.installmentcontractservice.model.InstallmentProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstallmentProductRepository: JpaRepository<InstallmentProduct, Int> {
}