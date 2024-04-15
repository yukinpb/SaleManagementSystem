package com.example.fe.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fe.databinding.ActivityMainBinding
import com.example.fe.model.User
import com.example.fe.view.manage_customer.SearchCustomerActivity
import com.example.fe.view.manage_product.SearchProductActivity
import com.example.fe.view.sign_installment_contract.AddInstallmentCustomerActivity
import com.example.fe.view.statistic_customer_by_revenue.StatisticCustomerActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.extras?.getSerializable("user") as User

        binding.txtName.text = user.name

        binding.btnManageProduct.setOnClickListener() {
            // Handle button click
            val intent = Intent(this, SearchProductActivity::class.java)
            startActivity(intent)
        }

        binding.btnManageCustomer.setOnClickListener() {
            // Handle button click
            val intent = Intent(this, SearchCustomerActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignInstallmentContract.setOnClickListener() {
            // Handle button click
            val intent = Intent(this, AddInstallmentCustomerActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        binding.btnStatisticCustomerByRevenue.setOnClickListener() {
            // Handle button click
            val intent = Intent(this, StatisticCustomerActivity::class.java)
            startActivity(intent)
        }

    }
}