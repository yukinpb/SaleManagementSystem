package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemSearchInstallmentProductBinding
import com.example.fe.interfaces.OnProductClickListener
import com.example.fe.model.Product

class InstallmentProductSearchResultAdapter(
    private var products: MutableList<Product>,
    private val itemClickListener: OnProductClickListener
) : RecyclerView.Adapter<InstallmentProductSearchResultAdapter.InstallmentProductSearchResultViewHolder>() {

    fun setData(products: MutableList<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstallmentProductSearchResultViewHolder {
        val binding = ItemSearchInstallmentProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InstallmentProductSearchResultViewHolder(binding, itemClickListener)
    }

    override fun onBindViewHolder(holder: InstallmentProductSearchResultViewHolder, position: Int) {
        holder.bindView(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class InstallmentProductSearchResultViewHolder(
        private val binding: ItemSearchInstallmentProductBinding,
        private val itemClickListener: OnProductClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(product: Product) {
            binding.txtProductName.text = product.name
            binding.txtProductPrice.text = "Price: ${product.price}$"
            binding.txtProductRemaining.text = "Remaining: ${product.remaining}"

            itemView.setOnClickListener {
                itemClickListener.onItemClick(product)
            }
        }
    }
}