package com.example.installmentcontractservice.repository

import com.example.installmentcontractservice.model.InstallmentCollateral
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstallmentCollateralRepository: JpaRepository<InstallmentCollateral, Int>{
}