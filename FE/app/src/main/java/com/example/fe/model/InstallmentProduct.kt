package com.example.fe.model

import com.example.fe.model.dto.InstallmentProductDto
import java.io.Serializable


data class InstallmentProduct(
    var id: Int = 0,
    var quantity: Double = 0.0,
    var amount: Double = 0.0,
    var product: Product
): Serializable

fun InstallmentProduct.toDto(): InstallmentProductDto {
    return InstallmentProductDto(
        id = this.id,
        quantity = this.quantity,
        amount = this.amount,
        productId = this.product.id
    )
}
