package com.example.fe.interfaces

import com.example.fe.model.InstallmentProduct

interface OnInstallmentProductRemoveClickListener {
    fun onRemoveClick(installmentProduct: InstallmentProduct)
}