package com.example.fe.designpattern.command

import com.example.fe.model.Product
import com.example.fe.retrofit.ProductApi

class DeleteProductCommand(
    private val product: Product
): Command {
    override suspend fun execute() {
        ProductApi.retrofitService.deleteProduct(product.id)
    }

    override suspend fun undo() {
        ProductApi.retrofitService.addProduct(product)
    }

}