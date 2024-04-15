package com.example.fe.view.manage_product

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
import com.example.fe.adapter.ProductAdapter
import com.example.fe.databinding.ActivitySearchProductBinding
import com.example.fe.designpattern.command.DeleteProductCommand
import com.example.fe.designpattern.command.UndoCommand
import com.example.fe.interfaces.OnProductDeleteClickListener
import com.example.fe.interfaces.OnProductClickListener
import com.example.fe.model.Product
import com.example.fe.retrofit.ProductApi
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class SearchProductActivity : AppCompatActivity(), OnProductClickListener, OnProductDeleteClickListener {

    private lateinit var binding: ActivitySearchProductBinding
    private lateinit var productAdapter: ProductAdapter
    private var products = mutableListOf<Product>()
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Clear the search
            clearSearch()
        }
    }

    private fun clearSearch() {
        products.clear()
        productAdapter.setData(products)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBackSearchProduct.setOnClickListener {
            finish()
        }

        // Initialize the ProductAdapter
        productAdapter = ProductAdapter(products, this, this)
        binding.rcvProductList.layoutManager = LinearLayoutManager(this)
        binding.rcvProductList.adapter = productAdapter

        binding.imgSearchProduct.setOnClickListener {
            searchProducts(binding.edtSearchProduct.text.toString())
            binding.edtSearchProduct.text.clear()
            hideKeyboard()
        }

        binding.edtSearchProduct.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchProducts(binding.edtSearchProduct.text.toString())
                binding.edtSearchProduct.text.clear()
                hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.fabAddProduct.setOnClickListener {
            // Handle button click
            val intent = Intent(this, AddProductActivity::class.java)
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

    private fun searchProducts(name: String) {
        lifecycleScope.launch {
            products.clear()
            products.addAll(ProductApi.retrofitService.getProduct(name))
            productAdapter.setData(products)
        }
    }

    override fun onDeleteClick(product: Product) {
        AlertDialog.Builder(this)
            .setTitle("Delete Product")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes") { _, _ ->
                var indexProductDeleted = -1
                val deleteProductCommand = DeleteProductCommand(product)
                val undoCommand = UndoCommand(deleteProductCommand)
                lifecycleScope.launch {
                    undoCommand.execute()
                    indexProductDeleted = products.indexOf(product)
                    products.remove(product)
                    productAdapter.setData(products)
                }

                Snackbar.make(binding.root, "Product deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        lifecycleScope.launch {
                            undoCommand.undo()
                            products.add(indexProductDeleted, product)
                            productAdapter.setData(products)
                        }
                    }
                    .show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onItemClick(product: Product) {
        val intent = Intent(this, EditProductActivity::class.java)
        intent.putExtra("product", product)
        startForResult.launch(intent)
    }

}