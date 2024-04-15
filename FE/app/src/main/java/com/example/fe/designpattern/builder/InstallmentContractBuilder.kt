package com.example.fe.designpattern.builder

import com.example.fe.model.Customer
import com.example.fe.model.InstallmentCollateral
import com.example.fe.model.InstallmentContract
import com.example.fe.model.InstallmentProduct
import com.example.fe.model.LendingPartner
import com.example.fe.model.User

class InstallmentContractBuilder(
    private var contract: InstallmentContract = InstallmentContract()
): Builder {
    override fun setCreater(user: User): Builder {
        contract.user = user
        return this
    }

    override fun setInstallmentCustomer(customer: Customer): Builder {
        contract.customer = customer
        return this
    }

    override fun setInstallmentProduct(products: List<InstallmentProduct>): Builder {
        contract.products = products
        return this
    }

    override fun addLendingPartner(partner: LendingPartner): Builder {
        contract.lendingPartner = partner
        return this
    }

    override fun addCollateral(collaterals: List<InstallmentCollateral>): Builder {
        contract.collaterals = collaterals
        return this
    }

    override fun build(): InstallmentContract {
        return contract
    }
}