package com.example.lendingpartnerservice.controller

import com.example.lendingpartnerservice.model.LendingPartner
import com.example.lendingpartnerservice.service.LendingPartnerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/lending-partner")
@CrossOrigin
class LendingPartnerController(
        private val lendingPartnerService: LendingPartnerService
) {
    @GetMapping("/{id}")
    fun getLendingPartnerById(@PathVariable id: Int): ResponseEntity<LendingPartner> {
        val lendingPartner = lendingPartnerService.getLendingPartnerById(id)
        return if(lendingPartner != null) {
            ResponseEntity.ok(lendingPartner)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/search")
    fun searchLendingPartner(@RequestParam name: String, @RequestParam loanAmount: Double): List<LendingPartner> = lendingPartnerService.searchLendingPartner(name, loanAmount)

    @PostMapping
    fun addLendingPartner(@RequestBody lendingPartner: LendingPartner): ResponseEntity<LendingPartner> {
        val result = lendingPartnerService.addLendingPartner(lendingPartner)
        return if(result != null) ResponseEntity.ok(result) else ResponseEntity.notFound().build()
    }

    @PutMapping
    fun editLendingPartner(@RequestBody lendingPartner: LendingPartner): ResponseEntity<LendingPartner> {
        val result = lendingPartnerService.editLendingPartner(lendingPartner)
        return if(result != null) ResponseEntity.ok(result) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteLendingPartner(@PathVariable id: Int) = lendingPartnerService.deleteLendingPartner(id)

    @GetMapping("/search/loan-amount/{loanAmount}")
    fun getAllLendingPartnerWithLoanAmount(@PathVariable loanAmount: Double): List<LendingPartner> = lendingPartnerService.getAllLendingPartnerWithLoanAmount(loanAmount)
}