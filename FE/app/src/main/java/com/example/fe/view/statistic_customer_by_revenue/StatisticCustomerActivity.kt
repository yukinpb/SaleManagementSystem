package com.example.fe.view.statistic_customer_by_revenue

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.adapter.CustomSpinnerStatisticCustomerType
import com.example.fe.adapter.CustomerAdapter
import com.example.fe.adapter.StatisticCustomerAdapter
import com.example.fe.databinding.ActivityStatisticCustomerBinding
import com.example.fe.designpattern.decorator.CustomerStatistic
import com.example.fe.designpattern.decorator.CustomerStatisticByName
import com.example.fe.designpattern.decorator.CustomerStatisticByRemaining
import com.example.fe.designpattern.decorator.CustomerStatisticByRevenue
import com.example.fe.interfaces.OnCustomerClickListener
import com.example.fe.model.Customer
import com.example.fe.model.StatisticCustomer
import com.example.fe.model.calculate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticCustomerActivity : AppCompatActivity(), OnCustomerClickListener {
    private lateinit var binding: ActivityStatisticCustomerBinding
    private var statisticCustomers: MutableList<StatisticCustomer> = mutableListOf()
    private lateinit var adapter: StatisticCustomerAdapter
    private lateinit var customerStatistic: CustomerStatistic
    private lateinit var spinnerAdapter: CustomSpinnerStatisticCustomerType
    private lateinit var items: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStatisticCustomerCancel.setOnClickListener() {
            finish()
        }

        adapter = StatisticCustomerAdapter(statisticCustomers, this)
        binding.rcvStatisticCustomerList.layoutManager = LinearLayoutManager(this)
        binding.rcvStatisticCustomerList.adapter = adapter

        items = arrayOf("Select type", "By revenue", "By remaining")
        spinnerAdapter = CustomSpinnerStatisticCustomerType(this, R.layout.item_statistic_customer_type, items)
        binding.spnStatisticCustomerType.adapter = spinnerAdapter

        binding.btnStatisticCustomerSwap.setOnClickListener() {
            statisticCustomers.reverse()
            adapter.setData(statisticCustomers)
        }

        customerStatistic = CustomerStatisticByName()

        binding.spnStatisticCustomerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (parent?.getItemAtPosition(position)) {
                    "By revenue" -> lifecycleScope.launch {
                        showLoading()
                        val customerStatistic = CustomerStatisticByRevenue(customerStatistic)
                        val customers: List<Customer> = customerStatistic.statisticCustomer()
                        updateRecyclerView(customers)
                        hideLoading()
                    }
                    "By remaining" -> lifecycleScope.launch {
                        showLoading()
                        val customerStatistic = CustomerStatisticByRemaining(customerStatistic)
                        val customers: List<Customer> = customerStatistic.statisticCustomer()
                        updateRecyclerView(customers)
                        hideLoading()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private suspend fun updateRecyclerView(customers: List<Customer>) {
        withContext(Dispatchers.Main) {
            statisticCustomers.clear()
            statisticCustomers.addAll(customers.map { it.calculate() })
            adapter.setData(statisticCustomers)
        }
    }

    override fun onItemClick(customer: Customer) {
        val intent = Intent(this, CustomerContractDetailActivity::class.java)
        intent.putExtra("customer", customer)
        startActivity(intent)
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.viewOverlay.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.viewOverlay.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}