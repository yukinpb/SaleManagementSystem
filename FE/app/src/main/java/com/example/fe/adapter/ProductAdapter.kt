package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemProductBinding
import com.example.fe.interfaces.OnProductDeleteClickListener
import com.example.fe.interfaces.OnProductClickListener
import com.example.fe.model.Product

class ProductAdapter(
    private var products: MutableList<Product>,
    private val itemClickListener: OnProductClickListener,
    private val deleteClickListener: OnProductDeleteClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun setData(products: MutableList<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, itemClickListener, deleteClickListener)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val itemClickListener: OnProductClickListener,
        private val deleteClickListener: OnProductDeleteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(product: Product) {
            binding.txtProductName.text = product.name
            binding.txtProductPrice.text = "Price: ${product.price}$"
            binding.txtProductRemaining.text = "Remaining: ${product.remaining}"

            itemView.setOnClickListener {
                itemClickListener.onItemClick(product)
            }

            // Set the click listener for the delete icon
            binding.imgDeleteProduct.setOnClickListener {
                deleteClickListener.onDeleteClick(product)
            }
        }
    }
}