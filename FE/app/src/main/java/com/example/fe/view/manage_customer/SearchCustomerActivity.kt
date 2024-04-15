package com.example.fe.view.manage_customer

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.adapter.CustomerAdapter
import com.example.fe.databinding.ActivitySearchCustomerBinding
import com.example.fe.designpattern.command.DeleteCustomerCommand
import com.example.fe.designpattern.command.UndoCommand
import com.example.fe.interfaces.OnCustomerClickListener
import com.example.fe.interfaces.OnCustomerDeleteClickListener
import com.example.fe.model.Customer
import com.example.fe.model.User
import com.example.fe.retrofit.CustomerApi
import com.example.fe.view.sign_installment_contract.AddInstallmentProductActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SearchCustomerActivity : AppCompatActivity(), OnCustomerClickListener, OnCustomerDeleteClickListener {
    private lateinit var binding: ActivitySearchCustomerBinding
    private lateinit var customerAdapter: CustomerAdapter
    private var customers = mutableListOf<Customer>()
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

        binding.imgBackSearchCustomer.setOnClickListener {
            finish()
        }

        // Initialize the CustomerAdapter
        customerAdapter = CustomerAdapter(customers, this, this)
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
            intent.putExtra("from", "search_customer")
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

    override fun onDeleteClick(customer: Customer) {
        AlertDialog.Builder(this)
            .setTitle("Delete Customer")
            .setMessage("Are you sure you want to delete this customer?")
            .setPositiveButton("Yes") { _, _ ->
                var indexCustomerDeleted = -1
                val deleteCustomerCommand = DeleteCustomerCommand(customer)
                val undoCommand = UndoCommand(deleteCustomerCommand)
                lifecycleScope.launch {
                    undoCommand.execute()
                    indexCustomerDeleted = customers.indexOf(customer)
                    customers.remove(customer)
                    customerAdapter.setData(customers)
                }

                Snackbar.make(binding.root, "Customer deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        lifecycleScope.launch {
                            undoCommand.undo()
                            customers.add(indexCustomerDeleted, customer)
                            customerAdapter.setData(customers)
                        }
                    }
                    .show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onItemClick(customer: Customer) {
        val intent = Intent(this, EditCustomerActivity::class.java)
        intent.putExtra("customer", customer)
        startForResult.launch(intent)
    }
}