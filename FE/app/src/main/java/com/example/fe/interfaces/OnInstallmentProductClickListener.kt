package com.example.fe.interfaces

import com.example.fe.model.InstallmentProduct

interface OnInstallmentProductClickListener {
    fun onItemClicked(installmentProduct: InstallmentProduct)
}