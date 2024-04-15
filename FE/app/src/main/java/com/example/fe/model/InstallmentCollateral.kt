package com.example.fe.model

import com.example.fe.model.dto.InstallmentCollateralDto
import java.io.Serializable
import java.sql.Date

data class InstallmentCollateral(
    var id: Int = 0,
    var installmentDate: Date = Date(0),
    var value: Double = 0.0,
    var collateral: Collateral
): Serializable

fun InstallmentCollateral.toDto(): InstallmentCollateralDto {
    return InstallmentCollateralDto(
        id = this.id,
        installmentDate = this.installmentDate,
        value = this.value,
        collateralId = this.collateral.id
    )
}