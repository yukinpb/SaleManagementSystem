package com.example.fe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fe.databinding.ItemStatisticCustomerBinding
import com.example.fe.interfaces.OnCustomerClickListener
import com.example.fe.model.Customer
import com.example.fe.model.StatisticCustomer
import com.example.fe.retrofit.CustomerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticCustomerAdapter(
    private var customers: MutableList<StatisticCustomer>,
    private val onItemClick: OnCustomerClickListener
) : RecyclerView.Adapter<StatisticCustomerAdapter.StatisticCustomerViewHolder>() {

    class StatisticCustomerViewHolder(
        private val binding: ItemStatisticCustomerBinding,
        private val onItemClick: OnCustomerClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(statisticCustomer: StatisticCustomer) {
            binding.txtStatisticCustomerName.text = statisticCustomer.customer.name
            binding.txtStatisticCustomerRevenue.text = "Revenue: ${statisticCustomer.revenue}$"
            binding.txtStatisticCustomerNumberOfContract.text = "Number of contract: ${statisticCustomer.numberOfContract}"
            binding.txtStatisticCustomerRemaining.text = "Remaining: ${statisticCustomer.remaining}$"

            itemView.setOnClickListener() {
                onItemClick.onItemClick(statisticCustomer.customer)
            }
        }
    }

    fun setData(customers: MutableList<StatisticCustomer>) {
        this.customers = customers
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticCustomerViewHolder {
        val binding = ItemStatisticCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatisticCustomerViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: StatisticCustomerViewHolder, position: Int) {
        holder.bind(customers[position])
    }

    override fun getItemCount() = customers.size
}

