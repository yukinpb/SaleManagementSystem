package com.example.customerservice.repository

import com.example.customerservice.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<Customer, Int>{
    fun findByNameContainingOrderByNameAsc(name: String): List<Customer>
}