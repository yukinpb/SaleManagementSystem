package com.example.fe.interfaces

import com.example.fe.model.Product

interface OnProductDeleteClickListener {
    fun onDeleteClick(product: Product)
}