package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemInstallmentCollateralBinding
import com.example.fe.interfaces.OnInstallmentCollateralClickListener
import com.example.fe.interfaces.OnInstallmentCollateralRemoveClickListener
import com.example.fe.model.InstallmentCollateral

class InstallmentCollateralAdapter(
    private var collaterals: MutableList<InstallmentCollateral>,
    private val removeClickListener: OnInstallmentCollateralRemoveClickListener
) : RecyclerView.Adapter<InstallmentCollateralAdapter.InstallmentCollateralViewHolder>() {

    fun setData(collaterals: MutableList<InstallmentCollateral>) {
        this.collaterals = collaterals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentCollateralViewHolder {
        val binding = ItemInstallmentCollateralBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstallmentCollateralViewHolder(binding, removeClickListener)
    }

    override fun onBindViewHolder(holder: InstallmentCollateralViewHolder, position: Int) {
        holder.bindView(collaterals[position])
    }

    override fun getItemCount(): Int {
        return collaterals.size
    }

    class InstallmentCollateralViewHolder(
        private val binding: ItemInstallmentCollateralBinding,
        private val removeClickListener: OnInstallmentCollateralRemoveClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(installmentCollateral: InstallmentCollateral) {
            binding.txtInstallmentCollateralName.text = installmentCollateral.collateral.name
            binding.txtInstallmentCollateralValue.text = "Value: ${installmentCollateral.collateral.value}"
            binding.txtInstallmentCollateralCondition.text = "Condition: ${installmentCollateral.collateral.condition}"

            binding.txtRemoveInstallmentCollateral.setOnClickListener {
                removeClickListener.onRemoveClick(installmentCollateral)
            }
        }
    }
}