package com.example.fe.view.manage_product

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityEditProductBinding
import com.example.fe.model.Product
import com.example.fe.retrofit.ProductApi
import kotlinx.coroutines.launch

class EditProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var product = intent.getSerializableExtra("product") as Product

        binding.btnEditProductReset.setOnClickListener {
            binding.edtEditProductName.text.clear()
            binding.edtEditProductPrice.text.clear()
            binding.edtEditProductDescription.text.clear()
            binding.edtEditProductRemaining.text.clear()
        }

        binding.edtEditProductId.setText(product.id.toString())
        binding.edtEditProductName.setText(product.name)
        binding.edtEditProductPrice.setText(product.price.toString())
        binding.edtEditProductDescription.setText(product.des)
        binding.edtEditProductRemaining.setText(product.remaining.toString())

        binding.btnEditProductCancel.setOnClickListener {
            finish()
        }

        binding.edtEditProductName.setOnEditorActionListener() { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditProductPrice.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditProductPrice.setOnEditorActionListener() { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditProductDescription.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditProductDescription.setOnEditorActionListener() { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditProductRemaining.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditProductRemaining.setOnEditorActionListener() { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.btnEditProductConfirm.setOnClickListener {
            if(binding.edtEditProductName.text.isEmpty() || binding.edtEditProductPrice.text.isEmpty() || binding.edtEditProductRemaining.text.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else if(binding.edtEditProductPrice.text.toString().toDoubleOrNull() == null || binding.edtEditProductRemaining.text.toString().toDoubleOrNull() == null) {
                Toast.makeText(this, "Price and remaining must be numbers", Toast.LENGTH_SHORT).show()
            }
            else if(binding.edtEditProductPrice.text.toString().toDouble() < 0 || binding.edtEditProductRemaining.text.toString().toDouble() < 0) {
                Toast.makeText(this, "Price and remaining must be greater than or equal to 0", Toast.LENGTH_SHORT).show()
            }
            else {
                product.name = binding.edtEditProductName.text.toString()
                product.price = binding.edtEditProductPrice.text.toString().toDouble()
                product.des = binding.edtEditProductDescription.text.toString()
                product.remaining = binding.edtEditProductRemaining.text.toString().toDouble()

                lifecycleScope.launch {
                    // Save the product
                    ProductApi.retrofitService.editProduct(product)
                    finish()
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