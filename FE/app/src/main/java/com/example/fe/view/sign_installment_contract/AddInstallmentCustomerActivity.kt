package com.example.fe.view.sign_installment_contract

import InstallmentCustomerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.databinding.ActivitySearchCustomerBinding
import com.example.fe.designpattern.builder.InstallmentContractBuilder
import com.example.fe.interfaces.OnCustomerClickListener
import com.example.fe.model.Customer
import com.example.fe.model.User
import com.example.fe.retrofit.CustomerApi
import com.example.fe.view.manage_customer.AddCustomerActivity
import kotlinx.coroutines.launch

class AddInstallmentCustomerActivity : AppCompatActivity(), OnCustomerClickListener {
    private lateinit var binding: ActivitySearchCustomerBinding
    private lateinit var customerAdapter: InstallmentCustomerAdapter
    private var customers = mutableListOf<Customer>()
    private var user = User()
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Clear the search
            clearSearch()
        }
    }

    private fun clearSearch() {
        customers.clear()
        customerAdapter.setData(customers)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.extras?.getSerializable("user") as User

        binding.imgBackSearchCustomer.setOnClickListener {
            finish()
        }

        // Initialize the CustomerAdapter
        customerAdapter = InstallmentCustomerAdapter(customers, this)
        binding.rcvCustomerList.layoutManager = LinearLayoutManager(this)
        binding.rcvCustomerList.adapter = customerAdapter

        binding.imgSearchCustomer.setOnClickListener {
            searchCustomers(binding.edtSearchCustomer.text.toString())
            binding.edtSearchCustomer.text.clear()
            hideKeyboard()
        }

        binding.edtSearchCustomer.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchCustomers(binding.edtSearchCustomer.text.toString())
                binding.edtSearchCustomer.text.clear()
                hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.fabAddCustomer.setOnClickListener {
            // Handle button click
            val intent = Intent(this, AddCustomerActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("from", "search_installment_customer")
            startForResult.launch(intent)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = currentFocus

        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun searchCustomers(name: String) {
        lifecycleScope.launch {
            customers.clear()
            customers.addAll(CustomerApi.retrofitService.getCustomer(name))
            customerAdapter.setData(customers)
        }
    }

    override fun onItemClick(customer: Customer) {
        val intent = Intent(this, AddInstallmentProductActivity::class.java)
        val installmentContract = InstallmentContractBuilder()
            .setInstallmentCustomer(customer)
            .setCreater(user!!)
            .build()
        intent.putExtra("installmentContract", installmentContract)
        startForResult.launch(intent)
    }
}