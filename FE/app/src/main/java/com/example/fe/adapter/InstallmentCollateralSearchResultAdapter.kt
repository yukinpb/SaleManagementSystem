package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemSearchInstallmentCollateralBinding
import com.example.fe.interfaces.OnCollateralClickListener
import com.example.fe.model.Collateral

class InstallmentCollateralSearchResultAdapter(
    private var collaterals: MutableList<Collateral>,
    private val itemClickListener: OnCollateralClickListener
) : RecyclerView.Adapter<InstallmentCollateralSearchResultAdapter.InstallmentCollateralSearchResultViewHolder>() {

    fun setData(collaterals: MutableList<Collateral>) {
        this.collaterals = collaterals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentCollateralSearchResultViewHolder {
        val binding = ItemSearchInstallmentCollateralBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstallmentCollateralSearchResultViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: InstallmentCollateralSearchResultViewHolder, position: Int) {
        holder.bindView(collaterals[position])
    }

    override fun getItemCount(): Int {
        return collaterals.size
    }

    class InstallmentCollateralSearchResultViewHolder(
        private val binding: ItemSearchInstallmentCollateralBinding,
        private val itemClickListener: OnCollateralClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(collateral: Collateral) {
            binding.txtCollateralName.text = collateral.name
            binding.txtCollateralValue.text = "Value: ${collateral.value}$"
            binding.txtCollateralCondition.text = "Condition: ${collateral.condition}"

            itemView.setOnClickListener {
                itemClickListener.onItemClick(collateral)
            }
        }
    }
}