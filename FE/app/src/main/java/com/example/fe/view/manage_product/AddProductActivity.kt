package com.example.fe.view.manage_product

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityAddProductBinding
import com.example.fe.model.Product
import com.example.fe.retrofit.ProductApi
import kotlinx.coroutines.launch

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddProductReset.setOnClickListener {
            binding.edtAddProductName.text.clear()
            binding.edtAddProductPrice.text.clear()
            binding.edtAddProductDescription.text.clear()
            binding.edtAddProductRemaining.text.clear()
        }

        binding.btnAddProductCancel.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        binding.edtAddProductName.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddProductPrice.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddProductPrice.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddProductDescription.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddProductDescription.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddProductRemaining.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddProductRemaining.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.btnAddProductConfirm.setOnClickListener {
            when {
                binding.edtAddProductName.text.isEmpty() -> {
                    binding.edtAddProductName.requestFocus()
                    binding.edtAddProductName.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddProductPrice.text.isEmpty() -> {
                    binding.edtAddProductPrice.requestFocus()
                    binding.edtAddProductPrice.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddProductRemaining.text.isEmpty() -> {
                    binding.edtAddProductRemaining.requestFocus()
                    binding.edtAddProductRemaining.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddProductPrice.text.toString().toDoubleOrNull() == null -> {
                    binding.edtAddProductPrice.requestFocus()
                    binding.edtAddProductPrice.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Price must be a number", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddProductRemaining.text.toString().toDoubleOrNull() == null -> {
                    binding.edtAddProductRemaining.requestFocus()
                    binding.edtAddProductRemaining.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Remaining must be a number", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddProductPrice.text.toString().toDouble() < 0 -> {
                    binding.edtAddProductPrice.requestFocus()
                    binding.edtAddProductPrice.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Price must be greater than or equal to 0", Toast.LENGTH_SHORT).show()
                }
                binding.edtAddProductRemaining.text.toString().toDouble() < 0 -> {
                    binding.edtAddProductRemaining.requestFocus()
                    binding.edtAddProductRemaining.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Remaining must be greater than or equal to 0", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    lifecycleScope.launch {
                        // Save the product
                        ProductApi.retrofitService.addProduct(
                            Product(
                                name = binding.edtAddProductName.text.toString(),
                                price = binding.edtAddProductPrice.text.toString().toDouble(),
                                des = binding.edtAddProductDescription.text.toString(),
                                remaining = binding.edtAddProductRemaining.text.toString().toDouble()
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