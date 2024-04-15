package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemCustomerBinding
import com.example.fe.interfaces.OnCustomerClickListener
import com.example.fe.interfaces.OnCustomerDeleteClickListener
import com.example.fe.model.Customer

class CustomerAdapter(
    private var customers: MutableList<Customer>,
    private val itemClickListener: OnCustomerClickListener,
    private val deleteClickListener: OnCustomerDeleteClickListener
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    fun setData(customers: MutableList<Customer>) {
        this.customers = customers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding, itemClickListener, deleteClickListener)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bindView(customers[position])
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    class CustomerViewHolder(
        private val binding: ItemCustomerBinding,
        private val itemClickListener: OnCustomerClickListener,
        private val deleteClickListener: OnCustomerDeleteClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(customer: Customer) {
            binding.txtCustomerName.text = customer.name
            binding.txtCustomerJob.text = "Job: ${customer.job}"
            binding.txtCustomerPhoneNumber.text = "Phone number: ${customer.phoneNumber}"

            itemView.setOnClickListener {
                itemClickListener.onItemClick(customer)
            }

            // Set the click listener for the delete icon
            binding.imgDeleteCustomer.setOnClickListener {
                deleteClickListener.onDeleteClick(customer)
            }
        }
    }
}