package com.example.customerservice.service

import com.example.customerservice.model.Customer
import com.example.customerservice.repository.CustomerRepository
import com.example.installmentcontractservice.model.InstallmentContract
import com.example.installmentcontractservice.model.Payment
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

enum class PaymentStatus {
    PENDING,
    PAID,
    OVERDUE
}

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val webClientBuilder: WebClient.Builder
) {
    fun addCustomer(customer: Customer): Customer? {
        if (!checkInfoValid(customer)) {
            return null
        }
        return customerRepository.save(customer)
    }

    private fun checkInfoValid(customer: Customer): Boolean {
        return customer.job.isNotEmpty() && customer.workPlace.isNotEmpty() && customer.bankCardNumber.isNotEmpty()
    }

    fun editCustomer(customer: Customer): Customer? {
        if (!checkInfoValid(customer)) {
            return null
        }
        return customerRepository.save(customer)
    }

    fun deleteCustomer(id: Int) {
        customerRepository.deleteById(id)
    }

    fun searchCustomer(name: String): List<Customer> {
        return customerRepository.findByNameContainingOrderByNameAsc(name)
    }

    fun getAllCustomersSortedByRevenueDesc(customers: List<Customer>): List<Customer> {
        return customers.sortedByDescending { calculateRevenue(it) }
    }

    fun getAllCustomersSortedByRemainingDesc(customers: List<Customer>): List<Customer> {
        return customers.sortedByDescending { calculateAmountRemaining(it) }
    }

    fun getAllCustomerSortedByName(): List<Customer> {
        return customerRepository.findAll().sortedBy { it.name }
    }

    fun calculateRevenue(customer: Customer): Double {
        val contracts = getAllContract(customer)
        return contracts.sumOf {
            getAllContractPayment(it.id).filter {
                    payment ->  payment.status == PaymentStatus.PAID.toString()
            }.sumOf {
                    payment -> payment.amountPaid
            }
        }
    }

    fun getNumberOfContract(customer: Customer): Int {
        return webClientBuilder.build().get()
            .uri("http://installment-contract-service/api/installment-contract/search/${customer.id}")
            .retrieve()
            .bodyToMono(Array<InstallmentContract>::class.java)
            .map { it.size }
            .block() ?: 0
    }

    fun calculateAmountRemaining(customer: Customer): Double {
        return webClientBuilder.build().get()
            .uri("http://installment-contract-service/api/installment-contract/search/${customer.id}")
            .retrieve()
            .bodyToMono(Array<InstallmentContract>::class.java)
            .map { contracts ->
                contracts.sumOf { it.amountRemaining }
            }
            .block() ?: 0.0
    }

    private fun getAllContract(customer: Customer): List<InstallmentContract> {
        return webClientBuilder.build().get()
            .uri("http://installment-contract-service/api/installment-contract/search/${customer.id}")
            .retrieve()
            .bodyToMono(Array<InstallmentContract>::class.java)
            .map { it.toList() }
            .block() ?: emptyList()
    }

    private fun getAllContractPayment(contractId: Int): List<Payment> {
        return webClientBuilder.build().get()
            .uri("http://payment-service/api/payment/search/$contractId")
            .retrieve()
            .bodyToMono(Array<Payment>::class.java)
            .map { it.toList() }
            .block() ?: emptyList()
    }

}