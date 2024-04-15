package com.example.fe.view.statistic_customer_by_revenue

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.R
import com.example.fe.adapter.InstallmentContractAdapter
import com.example.fe.databinding.ActivityCustomerContractDetailBinding
import com.example.fe.model.Customer
import com.example.fe.model.InstallmentContract
import com.example.fe.model.dto.InstallmentContractDto
import com.example.fe.retrofit.InstallmentContractApi
import kotlinx.coroutines.launch

class CustomerContractDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerContractDetailBinding
    private var contracts = mutableListOf<InstallmentContractDto>()
    private lateinit var adapter: InstallmentContractAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerContractDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCustomerContractDetailCancel.setOnClickListener() {
            finish()
        }

        val customer = intent.getSerializableExtra("customer") as Customer
        binding.txtCustomerNameContractDetail.text = customer.name

        lifecycleScope.launch {
            contracts.addAll(InstallmentContractApi.retrofitService.getInstallmentContractByCustomer(customer.id))
            adapter = InstallmentContractAdapter(contracts)
            binding.rcvCustomerContractList.layoutManager = LinearLayoutManager(this@CustomerContractDetailActivity)
            binding.rcvCustomerContractList.adapter = adapter
        }
    }
}