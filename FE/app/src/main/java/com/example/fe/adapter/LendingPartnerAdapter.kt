package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemLendingPartnerBinding
import com.example.fe.interfaces.OnLendingPartnerClickListener
import com.example.fe.model.LendingPartner

class LendingPartnerAdapter(
    private var lendingPartners: MutableList<LendingPartner>,
    private val itemClickListener: OnLendingPartnerClickListener
) : RecyclerView.Adapter<LendingPartnerAdapter.LendingPartnerViewHolder>() {

    fun setData(lendingPartners: MutableList<LendingPartner>) {
        this.lendingPartners = lendingPartners
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LendingPartnerViewHolder {
        val binding = ItemLendingPartnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LendingPartnerViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: LendingPartnerViewHolder, position: Int) {
        holder.bind(lendingPartners[position])
    }

    override fun getItemCount() = lendingPartners.size

    class LendingPartnerViewHolder(
        private val binding: ItemLendingPartnerBinding,
        private val itemClickListener: OnLendingPartnerClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lendingPartner: LendingPartner) {
            binding.apply {
                txtLendingPartnerName.text = lendingPartner.name
                txtLendingPartnerCompanyName.text = "Company: ${lendingPartner.companyName}"
                txtLendingPartnerMaximumLoan.text = "Maximum loan: ${lendingPartner.maximumLoan}"
                txtLendingPartnerAnnualInterestRate.text = "Annual interest rate: ${lendingPartner.annualInterestRate}%"
                txtLendingPartnerInterestRateIncrease.text = "Interest rate increase: ${lendingPartner.interestRateIncrease}%"

                txtSelectInstallmentCustomer.setOnClickListener {
                    itemClickListener.onItemClick(lendingPartner)
                }
            }
        }
    }
}