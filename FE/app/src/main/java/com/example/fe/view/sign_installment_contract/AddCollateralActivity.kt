package com.example.fe.view.sign_installment_contract

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityAddCollateralBinding
import com.example.fe.model.Collateral
import com.example.fe.retrofit.CollateralApi
import kotlinx.coroutines.launch

class AddCollateralActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCollateralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCollateralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddCollateralReset.setOnClickListener {
            binding.edtAddCollateralName.text.clear()
            binding.edtAddCollateralType.text.clear()
            binding.edtAddCollateralValue.text.clear()
            binding.edtAddCollateralCondition.text.clear()
        }

        binding.btnAddCollateralCancel.setOnClickListener {
            finish()
        }

        binding.edtAddCollateralName.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCollateralType.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCollateralType.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCollateralValue.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCollateralValue.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCollateralCondition.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCollateralCondition.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.btnAddCollateralConfirm.setOnClickListener {
            when {
                binding.edtAddCollateralName.text.isEmpty() -> {
                    binding.edtAddCollateralName.requestFocus()
                    binding.edtAddCollateralName.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddCollateralType.text.isEmpty() -> {
                    binding.edtAddCollateralType.requestFocus()
                    binding.edtAddCollateralType.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddCollateralValue.text.isEmpty() -> {
                    binding.edtAddCollateralValue.requestFocus()
                    binding.edtAddCollateralValue.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddCollateralCondition.text.toString().toDoubleOrNull() == null -> {
                    binding.edtAddCollateralCondition.requestFocus()
                    binding.edtAddCollateralCondition.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Price must be a number", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddCollateralValue.text.toString().toDoubleOrNull() == null -> {
                    binding.edtAddCollateralValue.requestFocus()
                    binding.edtAddCollateralValue.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Remaining must be a number", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddCollateralValue.text.toString().toDouble() < 0 -> {
                    binding.edtAddCollateralValue.requestFocus()
                    binding.edtAddCollateralValue.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Price must be greater than or equal to 0", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    lifecycleScope.launch {
                        // Save the product
                        CollateralApi.retrofitService.addCollateral(
                            Collateral(
                                name = binding.edtAddCollateralName.text.toString(),
                                type = binding.edtAddCollateralType.text.toString(),
                                value = binding.edtAddCollateralValue.text.toString().toDouble(),
                                condition = binding.edtAddCollateralCondition.text.toString()
                            )
                        )
                        setResult(RESULT_OK)
                        finish()
                    }
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
}