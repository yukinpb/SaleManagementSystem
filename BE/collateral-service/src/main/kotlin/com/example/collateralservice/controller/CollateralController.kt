package com.example.collateralservice.controller

import com.example.collateralservice.model.Collateral
import com.example.collateralservice.service.CollateralService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/collateral")
@CrossOrigin
class CollateralController(
    private val collateralService: CollateralService
) {
    @GetMapping("/{id}")
    fun getCollateralById(@PathVariable id: Int): ResponseEntity<Collateral> {
        val collateral = collateralService.getCollateralById(id)
        return if (collateral != null) {
            ResponseEntity.ok(collateral)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/add")
    fun addCollateral(@RequestBody collateral: Collateral): ResponseEntity<Collateral> {
        val result = collateralService.addCollateral(collateral)
        return if (result != null) ResponseEntity.ok(result) else ResponseEntity.notFound().build()
    }

    @GetMapping("/search/{customerId}")
    fun searchCollateral(@PathVariable customerId: Int): ResponseEntity<List<Collateral>> {
        val result = collateralService.searchCollateralByCustomer(customerId)
        return ResponseEntity.ok(result)
    }

    @PutMapping
    fun editCollateral(@RequestBody collateral: Collateral): ResponseEntity<Collateral> {
        val result = collateralService.editCollateral(collateral)
        return if (result != null) ResponseEntity.ok(result) else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteCollateral(@PathVariable id: Int): ResponseEntity<Unit> {
        collateralService.deleteCollateral(id)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/check-value/{loanAmount}")
    fun checkCollateralsValue(@RequestBody collaterals: List<Collateral>, @PathVariable loanAmount: Double): ResponseEntity<Boolean> {
        val result = collateralService.checkCollateralsValue(collaterals, loanAmount)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/search-by-name-and-customer")
    fun searchCollateralByCustomerAndName(@RequestParam customerId: Int,@RequestParam name: String): List<Collateral> {
        return collateralService.searchCollateralByCustomerAndName(customerId, name)
    }
}