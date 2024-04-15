package com.example.fe.interfaces

import com.example.fe.model.LendingPartner

interface OnLendingPartnerClickListener {
    fun onItemClick(lendingPartner: LendingPartner)
}