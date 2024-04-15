package com.example.fe.view.sign_installment_contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.adapter.InstallmentCollateralAdapter
import com.example.fe.adapter.InstallmentCollateralSearchResultAdapter
import com.example.fe.databinding.ActivityAddInstallmentCollateralBinding
import com.example.fe.designpattern.builder.InstallmentContractBuilder
import com.example.fe.interfaces.OnCollateralClickListener
import com.example.fe.interfaces.OnInstallmentCollateralRemoveClickListener
import com.example.fe.model.InstallmentContract
import com.example.fe.model.InstallmentCollateral
import com.example.fe.model.Collateral
import com.example.fe.retrofit.CollateralApi
import kotlinx.coroutines.launch
import java.util.Date

class AddInstallmentCollateralActivity : AppCompatActivity(), OnCollateralClickListener, OnInstallmentCollateralRemoveClickListener {
    private lateinit var binding: ActivityAddInstallmentCollateralBinding
    private lateinit var collateralAdapter: InstallmentCollateralSearchResultAdapter
    private lateinit var installmentCollateralAdapter: InstallmentCollateralAdapter
    private var collaterals = mutableListOf<Collateral>()
    private var installmentCollaterals = mutableListOf<InstallmentCollateral>()
    private var installmentContract = InstallmentContract()
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Clear the search
            clearSearch()
        }
    }

    private fun clearSearch() {
        collaterals.clear()
        collateralAdapter.setData(collaterals)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInstallmentCollateralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        installmentContract = intent.getSerializableExtra("installmentContract") as InstallmentContract

        if(installmentContract != null) {
            binding.txtLoanAmountAddInstallmentCollateral.text = "Loan amount: ${installmentContract.products.sumOf { it.amount }}$"
        }

        binding.imgBackAddInstallmentCollateral.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        // Initialize the CollateralAdapter
        collateralAdapter = InstallmentCollateralSearchResultAdapter(collaterals, this)
        binding.rcvCollateralList.layoutManager = LinearLayoutManager(this)
        binding.rcvCollateralList.adapter = collateralAdapter

        // Initialize the InstallmentCollateralAdapter
        installmentCollateralAdapter = InstallmentCollateralAdapter(installmentCollaterals, this)
        binding.rcvInstallmentCollateralList.layoutManager = LinearLayoutManager(this)
        binding.rcvInstallmentCollateralList.adapter = installmentCollateralAdapter

        binding.imgSearchCollateral.setOnClickListener {
            searchCollaterals(binding.edtSearchCollateral.text.toString())
            binding.edtSearchCollateral.text.clear()
            hideKeyboard()
        }

        binding.edtSearchCollateral.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchCollaterals(binding.edtSearchCollateral.text.toString())
                binding.edtSearchCollateral.text.clear()
                hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.btnConfirmAddInstallmentCollateral.setOnClickListener {
            if(installmentCollaterals.isEmpty()) {
                Toast.makeText(this, "Please add at least one collateral", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(installmentContract.products.sumOf { it.amount } > installmentCollaterals.sumOf { it.value }) {
                Toast.makeText(this, "Total value of collaterals must not exceed loan amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var installmentContract = intent.getSerializableExtra("installmentContract") as InstallmentContract
            if(installmentContract != null) {
                val intent = Intent(this, AddInstallmentContractActivity::class.java)
                installmentContract = InstallmentContractBuilder(installmentContract)
                    .addCollateral(installmentCollaterals)
                    .build()
                intent.putExtra("installmentContract", installmentContract)
                startForResult.launch(intent)
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = currentFocus

        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun searchCollaterals(name: String) {
        lifecycleScope.launch {
            collaterals.clear()
            collaterals.addAll(CollateralApi.retrofitService.searchCollaterals(name, installmentContract.customer!!.id))
            collateralAdapter.setData(collaterals)
        }
    }

    override fun onItemClick(collateral: Collateral) {
        if(installmentCollaterals.any { it.collateral.id == collateral.id }) {
            Toast.makeText(this, "Collateral already added", Toast.LENGTH_SHORT).show()
            return
        }
        val installmentCollateral = InstallmentCollateral(
            value = collateral.value,
            installmentDate = java.sql.Date(Date().time),
            collateral = collateral
        )
        installmentCollaterals.add(installmentCollateral)
        binding.txtTotalValueAddInstallmentCollateral.text = "Total value: ${installmentCollaterals.sumOf { it.value }}$"
        installmentCollateralAdapter.setData(installmentCollaterals)
    }

    override fun onRemoveClick(installmentCollateral: InstallmentCollateral) {
        installmentCollaterals.remove(installmentCollateral)

        installmentCollateralAdapter.setData(installmentCollaterals)
    }
}