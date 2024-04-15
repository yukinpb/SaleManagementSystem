package com.example.productservice.service

import com.example.productservice.model.Product
import com.example.productservice.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
        private val productRepository: ProductRepository
) {
    fun addProduct(product: Product): Product? {
        if(!checkInfoValid(product)) {
            return null
        }
        return productRepository.save(product)
    }

    private fun checkInfoValid(product: Product): Boolean {
        return product.name.isNotEmpty() && product.price > 0
    }

    fun editProduct(product: Product): Product? {
        if(!checkInfoValid(product)) {
            return null
        }
        return productRepository.save(product)
    }

    fun deleteProduct(id: Int) {
        productRepository.deleteById(id)
    }

    fun searchProduct(name: String): List<Product> {
        return productRepository.findByNameContainingIgnoreCaseOrderByNameAsc(name)
    }

    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun checkProductRemaining(productId: Int, quantity: Double): Boolean {
        return (getProductById(productId)?.remaining ?: 0.0) >= quantity
    }

    fun getProductById(productId: Int): Product? {
        return productRepository.findById(productId).orElse(null)
    }
}