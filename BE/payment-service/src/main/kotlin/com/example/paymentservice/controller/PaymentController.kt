package com.example.paymentservice.controller

import com.example.paymentservice.model.Payment
import com.example.paymentservice.service.PaymentService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/payment")
@CrossOrigin
class PaymentController(
    private val paymentService: PaymentService
) {
    @GetMapping("/search/{contractId}")
    fun getPaymentsByContract(@PathVariable contractId: Int) = paymentService.getPaymentsByContract(contractId)

    @PostMapping("/create/{contractId}")
    @CircuitBreaker(name = "payment")
    @TimeLimiter(name = "payment")
    @Retry(name = "payment")
    fun createPayment(@PathVariable contractId: Int): CompletableFuture<ResponseEntity<Payment>> {
        return CompletableFuture.supplyAsync {
            val payment = paymentService.createPayment(contractId)
            if(payment != null) ResponseEntity.ok(payment) else ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/update/penalty")
    @CircuitBreaker(name = "payment")
    @TimeLimiter(name = "payment")
    @Retry(name = "payment")
    fun updatePaymentPenalty(@RequestBody payment: Payment): CompletableFuture<ResponseEntity<Payment>> {
        return CompletableFuture.supplyAsync {
            val updatedPayment = paymentService.updatePaymentPenalty(payment)
            if(updatedPayment != null) ResponseEntity.ok(updatedPayment) else ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/update/paid")
    @CircuitBreaker(name = "payment")
    @TimeLimiter(name = "payment")
    @Retry(name = "payment")
    fun updatePaymentPaid(@RequestBody payment: Payment): CompletableFuture<ResponseEntity<Payment>> {
        return CompletableFuture.supplyAsync {
            val updatedPayment = paymentService.updatePaymentPaid(payment)
            ResponseEntity.ok(updatedPayment)
        }
    }
}
