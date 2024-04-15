package com.example.fe.view.sign_installment_contract

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
import com.example.fe.adapter.LendingPartnerAdapter
import com.example.fe.databinding.ActivityAddLendingPartnerBinding
import com.example.fe.designpattern.builder.InstallmentContractBuilder
import com.example.fe.interfaces.OnLendingPartnerClickListener
import com.example.fe.model.InstallmentContract
import com.example.fe.model.LendingPartner
import com.example.fe.retrofit.LendingPartnerApi
import kotlinx.coroutines.launch

class AddLendingPartnerActivity : AppCompatActivity(), OnLendingPartnerClickListener {
    private lateinit var binding: ActivityAddLendingPartnerBinding
    private lateinit var adapter: LendingPartnerAdapter
    private var lendingPartners = mutableListOf<LendingPartner>()
    private var installmentContract = InstallmentContract()
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Clear the search
            clearSearch()
        }
    }

    private fun clearSearch() {
        lendingPartners.clear()
        adapter.setData(lendingPartners)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLendingPartnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackAddLendingPartner.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        adapter = LendingPartnerAdapter(lendingPartners, this)
        binding.rcvLendingPartnerList.layoutManager = LinearLayoutManager(this)
        binding.rcvLendingPartnerList.adapter = adapter

        installmentContract = intent.getSerializableExtra("installmentContract") as InstallmentContract
        if(installmentContract != null) {
            val totalAmount = installmentContract.products.sumOf { it.amount }

            binding.imgSearchLendingPartner.setOnClickListener {
                searchLendingPartners(binding.edtSearchLendingPartner.text.toString(), totalAmount)
                binding.edtSearchLendingPartner.text.clear()
                hideKeyboard()
            }

            binding.edtSearchLendingPartner.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchLendingPartners(binding.edtSearchLendingPartner.text.toString(), totalAmount)
                    binding.edtSearchLendingPartner.text.clear()
                    hideKeyboard()
                    true
                } else {
                    false
                }
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

    private fun searchLendingPartners(name: String, loanAmount: Double) {
        lifecycleScope.launch {
            lendingPartners.clear()
            lendingPartners.addAll(LendingPartnerApi.retrofitService.searchLendingPartners(name, loanAmount))
            adapter.setData(lendingPartners)
        }
    }

    override fun onItemClick(lendingPartner: LendingPartner) {
        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to select this lending partner?")
            .setPositiveButton("Yes") { _, _ ->
                installmentContract = InstallmentContractBuilder(installmentContract)
                    .addLendingPartner(lendingPartner)
                    .build()
                val intent = Intent(this, AddInstallmentCollateralActivity::class.java)
                intent.putExtra("installmentContract", installmentContract)
                startForResult.launch(intent)
            }
            .setNegativeButton("No", null)
            .show()
    }
}