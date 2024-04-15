package com.example.installmentcontractservice.controller

import com.example.installmentcontractservice.model.InstallmentContract
import com.example.installmentcontractservice.service.InstallmentContractService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/installment-contract")
@CrossOrigin
class InstallmentContractController(
    private val installmentContractService: InstallmentContractService
) {
    @PostMapping("/add")
    @CircuitBreaker(name = "installment-contract")
    @TimeLimiter(name = "installment-contract")
    @Retry(name = "installment-contract")
    fun addContract(@RequestBody installmentContract: InstallmentContract): CompletableFuture<ResponseEntity<InstallmentContract>> {
        val result = installmentContractService.addContract(installmentContract)
        return CompletableFuture.supplyAsync { ResponseEntity.ok(result) }
    }

    @GetMapping("/search/{customerId}")
    fun searchContractByCustomer(@PathVariable customerId: Int): List<InstallmentContract> = installmentContractService.searchContractByCustomer(customerId)

    @GetMapping("/{contractId}")
    fun getContractById(@PathVariable contractId: Int): ResponseEntity<InstallmentContract> {
        val contract = installmentContractService.getContractById(contractId)
        return if(contract != null) {
            ResponseEntity.ok(contract)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/update-contract-paid-finish/{contractId}")
    @CircuitBreaker(name = "installment-contract")
    @TimeLimiter(name = "installment-contract")
    @Retry(name = "installment-contract")
    fun updateContractPaidFinish(@PathVariable contractId: Int): CompletableFuture<ResponseEntity<InstallmentContract>> {
        val contract = installmentContractService.updateContractPaidFinish(contractId)
        return if(contract != null) {
            CompletableFuture.supplyAsync{ ResponseEntity.ok(contract) }
        } else {
            CompletableFuture.supplyAsync { ResponseEntity.notFound().build() }
        }
    }

    @PostMapping("/update")
    fun updateContract(@RequestBody contract: InstallmentContract): ResponseEntity<InstallmentContract> {
        val result = installmentContractService.updateContract(contract)
        return ResponseEntity.ok(result)
    }
}