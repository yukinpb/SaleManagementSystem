package com.example.installmentcontractservice.service

import com.example.installmentcontractservice.model.Collateral
import com.example.installmentcontractservice.model.InstallmentContract
import com.example.installmentcontractservice.model.LendingPartner
import com.example.installmentcontractservice.model.Product
import com.example.installmentcontractservice.repository.InstallmentCollateralRepository
import com.example.installmentcontractservice.repository.InstallmentContractRepository
import com.example.installmentcontractservice.repository.InstallmentProductRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

enum class CollateralStatus {
    ACTIVE,
    INACTIVE
}

enum class InstallmentContractStatus {
    PENDING,
    PAID
}

@Service
class InstallmentContractService(
    private val installmentContractRepository: InstallmentContractRepository,
    private val installmentCollateralRepository: InstallmentCollateralRepository,
    private val installmentProductRepository: InstallmentProductRepository,
    private val webClientBuilder: WebClient.Builder
) {
    fun getContractById(contractId: Int): InstallmentContract? = installmentContractRepository.findById(contractId).get()

    fun addContract(installmentContract: InstallmentContract): InstallmentContract {
        installmentContract.collaterals.forEach {
            val collateral = getCollateralInContract(it.collateralId)
            if (collateral != null) {
                updateCollateralStatus(collateral, CollateralStatus.INACTIVE.toString())
            }
        }
        installmentContract.products.forEach {
            val product = getProductInContract(it.productId)
            if (product != null) {
                updateProductRemaining(product, it.quantity)
            }
        }
        installmentContract.status = InstallmentContractStatus.PENDING.toString()
        installmentContract.createDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        installmentContract.amountRemaining = installmentContract.products.sumOf { it.amount }

        val contract = installmentContractRepository.save(installmentContract)
        contract.collaterals.forEach {
            it.contract = contract
            installmentCollateralRepository.save(it)
        }
        contract.products.forEach {
            it.contract = contract
            installmentProductRepository.save(it)
        }
        return contract
    }

    fun updateContractPaidFinish(contractId: Int): InstallmentContract? {
        val contract = getContractById(contractId) ?: return null
        contract.collaterals.forEach {
            val collateral = getCollateralInContract(it.collateralId)
            if (collateral != null) {
                updateCollateralStatus(collateral, CollateralStatus.ACTIVE.toString())
            }
        }
        contract.status = InstallmentContractStatus.PAID.toString()
        return installmentContractRepository.save(contract)
    }

    fun searchContractByCustomer(customerId: Int): List<InstallmentContract> = installmentContractRepository.findByCustomerId(customerId)

    private fun getCollateralInContract(collateralId: Int): Collateral? {
        return webClientBuilder.build().get()
            .uri("http://collateral-service/api/collateral/{collateralId}", collateralId)
            .retrieve()
            .bodyToMono(Collateral::class.java)
            .block()
    }

    private fun updateCollateralStatus(collateral: Collateral, status: String) {
        collateral.status = status
        webClientBuilder.build().put()
            .uri("http://collateral-service/api/collateral")
            .bodyValue(collateral)
            .retrieve()
            .bodyToMono(Collateral::class.java)
            .block()
    }

    private fun getProductInContract(productId: Int): Product? {
        return webClientBuilder.build().get()
            .uri("http://product-service/api/product/{productId}", productId)
            .retrieve()
            .bodyToMono(Product::class.java)
            .block()
    }

    private fun updateProductRemaining(product: Product, quantity: Double) {
        product.remaining -= quantity
        webClientBuilder.build().put()
            .uri("http://product-service/api/product/edit")
            .bodyValue(product)
            .retrieve()
            .bodyToMono(Product::class.java)
            .block()
    }

    fun updateContract(installmentContract: InstallmentContract): InstallmentContract {
        return installmentContractRepository.save(installmentContract)
    }


}