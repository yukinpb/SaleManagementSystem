package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemInstallmentContractBinding
import com.example.fe.model.dto.InstallmentContractDto

class InstallmentContractAdapter(private val contracts: List<InstallmentContractDto>) :
    RecyclerView.Adapter<InstallmentContractAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemInstallmentContractBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contract: InstallmentContractDto) {
            binding.txtContractAmountRemaining.text = "Amount remaining: ${contract.amountRemaining}$"
            binding.txtContractDayPaidEachMonth.text = "Day paid each month: ${contract.dayPaidEachMonth}"
            binding.txtContractPenaltyRate.text = "Penalty rate: ${contract.penaltyRate}%"
            binding.txtContractAnnualInterestRate.text = "Annual interest rate: ${contract.annualInterestRate}%"
            binding.txtContractInterestRateIncrease.text = "Interest rate increase: ${contract.interestRateIncrease}%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInstallmentContractBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contract = contracts[position]
        holder.bind(contract)
    }

    override fun getItemCount() = contracts.size
}