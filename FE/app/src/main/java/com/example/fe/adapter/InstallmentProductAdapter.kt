package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemInstallmentProductBinding
import com.example.fe.interfaces.OnInstallmentProductClickListener
import com.example.fe.interfaces.OnInstallmentProductRemoveClickListener
import com.example.fe.model.InstallmentProduct

class InstallmentProductAdapter(
    private var products: MutableList<InstallmentProduct>,
    private val itemClickListener: OnInstallmentProductClickListener,
    private val removeClickListener: OnInstallmentProductRemoveClickListener
) : RecyclerView.Adapter<InstallmentProductAdapter.InstallmentProductViewHolder>() {

    fun setData(products: MutableList<InstallmentProduct>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentProductViewHolder {
        val binding = ItemInstallmentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstallmentProductViewHolder(binding, itemClickListener, removeClickListener)
    }

    override fun onBindViewHolder(holder: InstallmentProductViewHolder, position: Int) {
        holder.bindView(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class InstallmentProductViewHolder(
        private val binding: ItemInstallmentProductBinding,
        private val itemClickListener: OnInstallmentProductClickListener,
        private val removeClickListener: OnInstallmentProductRemoveClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(installmentProduct: InstallmentProduct) {
            binding.txtInstallmentProductName.text = installmentProduct.product.name
            binding.txtInstallmentProductQuantity.text = "Quantity: ${installmentProduct.quantity}"
            binding.txtInstallmentProductAmount.text = "Amount: ${installmentProduct.amount}$"

            itemView.setOnClickListener {
                itemClickListener.onItemClicked(installmentProduct)
            }

            binding.txtRemoveInstallmentProduct.setOnClickListener {
                removeClickListener.onRemoveClick(installmentProduct)
            }
        }
    }
}