package com.example.productservice.controller

import com.example.productservice.service.ProductService
import com.example.productservice.model.Product
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/product")
@CrossOrigin
class ProductController(
        private val productService: ProductService
) {
    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Int): ResponseEntity<Product?> {
        val product = productService.getProductById(id)
        return if (product != null) ResponseEntity.ok(product) else ResponseEntity.notFound().build()
    }

    @PostMapping("/add")
    fun addProduct(@RequestBody product: Product): ResponseEntity<Product?> {
        val result = productService.addProduct(product)
        return if (result != null) ResponseEntity.ok(result) else ResponseEntity.badRequest().build()
    }

    @PutMapping("/edit")
    fun editProduct(@RequestBody product: Product): ResponseEntity<Product?> {
        val result = productService.editProduct(product)
        return if (result != null) ResponseEntity.ok(result) else ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/delete/{id}")
    fun deleteProduct(@PathVariable id: Int): ResponseEntity<Void> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    @GetMapping("/search")
    fun searchProduct(@RequestParam name: String): ResponseEntity<List<Product>> {
        val products = productService.searchProduct(name)
        return ResponseEntity.ok(products)
    }

    @GetMapping("/check-remaining")
    fun checkProductRemaining(@RequestParam productId: Int, @RequestParam quantity: Double): Boolean {
        return productService.checkProductRemaining(productId, quantity)
    }

}