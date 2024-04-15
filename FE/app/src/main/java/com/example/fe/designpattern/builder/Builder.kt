package com.example.fe.designpattern.builder

import com.example.fe.model.Collateral
import com.example.fe.model.Customer
import com.example.fe.model.InstallmentCollateral
import com.example.fe.model.InstallmentContract
import com.example.fe.model.InstallmentProduct
import com.example.fe.model.LendingPartner
import com.example.fe.model.User

interface Builder {
    fun setCreater(user: User): Builder
    fun setInstallmentCustomer(customer: Customer): Builder
    fun setInstallmentProduct(products: List<InstallmentProduct>): Builder
    fun addLendingPartner(partner: LendingPartner): Builder
    fun addCollateral(collateral: List<InstallmentCollateral>): Builder
    fun build(): InstallmentContract
}