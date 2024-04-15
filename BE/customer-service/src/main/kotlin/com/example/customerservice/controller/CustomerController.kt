package com.example.customerservice.controller

import com.example.customerservice.service.CustomerService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.example.customerservice.model.Customer
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/customer")
@CrossOrigin
class CustomerController(private val customerService: CustomerService) {

    @PostMapping("/add")
    fun addCustomer(@RequestBody customer: Customer): ResponseEntity<Customer> {
        val newCustomer = customerService.addCustomer(customer)
        println(customer.toString())
        return if (newCustomer != null) {
            ResponseEntity.ok(newCustomer)
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @PutMapping("/edit")
    fun updateCustomer(@RequestBody customer: Customer): ResponseEntity<Customer> {
        val updatedCustomer = customerService.editCustomer(customer)
        return if (updatedCustomer != null) {
            ResponseEntity.ok(updatedCustomer)
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCustomer(@PathVariable id: Int) {
        customerService.deleteCustomer(id)
    }

    @GetMapping("/search")
    fun searchCustomer(@RequestParam name: String): List<Customer> {
        return customerService.searchCustomer(name)
    }

    @PostMapping("/sort-by-revenue")
    fun getAllCustomersSortedByRevenueDesc(@RequestBody customers: List<Customer>): List<Customer> {
        return customerService.getAllCustomersSortedByRevenueDesc(customers)
    }

    @PostMapping("/sort-by-remaining")
    fun getAllCustomersSortedByRemainingDesc(@RequestBody customers: List<Customer>): List<Customer> {
        return customerService.getAllCustomersSortedByRemainingDesc(customers)
    }

    @GetMapping("/all-by-name")
    fun getAllCustomerSortedByName(): List<Customer> {
        return customerService.getAllCustomerSortedByName()
    }

    @PostMapping("/revenue")
    @CircuitBreaker(name = "customer")
    @TimeLimiter(name = "customer")
    @Retry(name = "customer")
    fun calculateRevenue(@RequestBody customer: Customer): CompletableFuture<Double> {
        return CompletableFuture.supplyAsync { customerService.calculateRevenue(customer) }
    }

    @PostMapping("/contract")
    @CircuitBreaker(name = "customer")
    @TimeLimiter(name = "customer")
    @Retry(name = "customer")
    fun getNumberOfContract(@RequestBody customer: Customer): CompletableFuture<Int> {
        return CompletableFuture.supplyAsync { customerService.getNumberOfContract(customer) }
    }

    @PostMapping("/amount-remaining")
    @CircuitBreaker(name = "customer")
    @TimeLimiter(name = "customer")
    @Retry(name = "customer")
    fun calculateAmountRemaining(@RequestBody customer: Customer): CompletableFuture<Double> {
        return CompletableFuture.supplyAsync { customerService.calculateAmountRemaining(customer) }
    }
}