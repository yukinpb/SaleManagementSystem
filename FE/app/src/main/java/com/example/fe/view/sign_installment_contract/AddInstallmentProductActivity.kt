package com.example.fe.view.sign_installment_contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fe.adapter.InstallmentProductAdapter
import com.example.fe.adapter.InstallmentProductSearchResultAdapter
import com.example.fe.databinding.ActivityAddInstallmentProductBinding
import com.example.fe.databinding.CustomDialogSelectQuantityInstallmentProductBinding
import com.example.fe.designpattern.builder.InstallmentContractBuilder
import com.example.fe.interfaces.OnInstallmentProductClickListener
import com.example.fe.interfaces.OnInstallmentProductRemoveClickListener
import com.example.fe.interfaces.OnProductClickListener
import com.example.fe.model.InstallmentContract
import com.example.fe.model.InstallmentProduct
import com.example.fe.model.Product
import com.example.fe.retrofit.ProductApi
import kotlinx.coroutines.launch

class AddInstallmentProductActivity : AppCompatActivity(), OnProductClickListener, OnInstallmentProductClickListener, OnInstallmentProductRemoveClickListener {
    private lateinit var binding: ActivityAddInstallmentProductBinding
    private lateinit var productAdapter: InstallmentProductSearchResultAdapter
    private lateinit var installmentProductAdapter: InstallmentProductAdapter
    private var products = mutableListOf<Product>()
    private var installmentProducts = mutableListOf<InstallmentProduct>()
    private lateinit var dialogAddInstallmentProduct: AlertDialog
    private lateinit var dialogEditInstallmentProduct: AlertDialog
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
        binding = ActivityAddInstallmentProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialogAddInstallmentProduct = AlertDialog.Builder(this).create()
        dialogEditInstallmentProduct = AlertDialog.Builder(this).create()

        binding.imgBackSearchProduct.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        // Initialize the ProductAdapter
        productAdapter = InstallmentProductSearchResultAdapter(products, this)
        binding.rcvProductList.layoutManager = LinearLayoutManager(this)
        binding.rcvProductList.adapter = productAdapter

        // Initialize the InstallmentProductAdapter
        installmentProductAdapter = InstallmentProductAdapter(installmentProducts, this, this)
        binding.rcvInstallmentProductList.layoutManager = LinearLayoutManager(this)
        binding.rcvInstallmentProductList.adapter = installmentProductAdapter

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

        binding.btnConfirmAddInstallmentProduct.setOnClickListener {
            if(installmentProducts.isEmpty()) {
                Toast.makeText(this, "Please add at least one product", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var installmentContract = intent.getSerializableExtra("installmentContract") as InstallmentContract
            if(installmentContract != null) {
                val intent = Intent(this, AddLendingPartnerActivity::class.java)
                installmentContract = InstallmentContractBuilder(installmentContract)
                    .setInstallmentProduct(installmentProducts)
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

    private fun searchProducts(name: String) {
        lifecycleScope.launch {
            products.clear()
            products.addAll(ProductApi.retrofitService.getProduct(name))
            productAdapter.setData(products)
        }
    }

    override fun onItemClick(product: Product) {
        dialogAddInstallmentProduct = AlertDialog.Builder(this).create()
        val dialogBinding = CustomDialogSelectQuantityInstallmentProductBinding.inflate(layoutInflater)

        var quantity = 1
        var price = product.price
        dialogBinding.txtQuantityInstallmentProductSelected.text = quantity.toString()
        dialogBinding.txtPriceInstallmentProductSelected.text = "$price$"

        dialogBinding.btnDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                price = quantity * product.price
                dialogBinding.txtQuantityInstallmentProductSelected.text = quantity.toString()
                dialogBinding.txtPriceInstallmentProductSelected.text = "$price$"
            }
            else {
                Toast.makeText(this, "The quantity must be greater than 0", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.btnIncrease.setOnClickListener {
            if(quantity < product.remaining) {
                quantity++
                price = quantity * product.price
                dialogBinding.txtQuantityInstallmentProductSelected.text = quantity.toString()
                dialogBinding.txtPriceInstallmentProductSelected.text = "$price$"
            }
            else {
                Toast.makeText(this, "The quantity exceeds the remaining quantity", Toast.LENGTH_SHORT).show()
            }
        }

        dialogBinding.btnConfirmAddInstallmentProduct.setOnClickListener {
            val existingInstallmentProduct = installmentProducts.find { it.product.id == product.id }

            if (existingInstallmentProduct != null) {
                existingInstallmentProduct.quantity += quantity.toDouble()
                existingInstallmentProduct.amount += price
            } else {
                val installmentProduct = InstallmentProduct(
                    quantity = quantity.toDouble(),
                    amount = price,
                    product = product
                )
                installmentProducts.add(installmentProduct)
            }

            binding.txtTotalValueAddInstallmentProduct.text = "Total value: ${installmentProducts.sumOf { it.amount }}$"

            installmentProductAdapter.setData(installmentProducts)

            dialogAddInstallmentProduct.dismiss()
        }

        dialogBinding.btnCancelAddInstallmentProduct.setOnClickListener {
            dialogAddInstallmentProduct.dismiss()
        }

        dialogAddInstallmentProduct.setView(dialogBinding.root)

        dialogAddInstallmentProduct.show()
    }

    override fun onItemClicked(installmentProduct: InstallmentProduct) {
        dialogEditInstallmentProduct = AlertDialog.Builder(this).create()
        val dialogBinding = CustomDialogSelectQuantityInstallmentProductBinding.inflate(layoutInflater)

        var quantity = installmentProduct.quantity.toInt()
        var price = installmentProduct.amount
        dialogBinding.txtQuantityInstallmentProductSelected.text = quantity.toString()
        dialogBinding.txtPriceInstallmentProductSelected.text = "$price$"

        dialogBinding.btnDecrease.setOnClickListener {
            if (quantity > 1) {
                quantity--
                price = quantity * installmentProduct.product.price
                dialogBinding.txtQuantityInstallmentProductSelected.text = quantity.toString()
                dialogBinding.txtPriceInstallmentProductSelected.text = "$price$"
            }
        }

        dialogBinding.btnIncrease.setOnClickListener {
            if(quantity < installmentProduct.product.remaining) {
                quantity++
                price = quantity * installmentProduct.product.price
                dialogBinding.txtQuantityInstallmentProductSelected.text = quantity.toString()
                dialogBinding.txtPriceInstallmentProductSelected.text = "$price$"
            }
        }

        dialogBinding.btnConfirmAddInstallmentProduct.setOnClickListener {
            installmentProduct.quantity = quantity.toDouble()
            installmentProduct.amount = price
            installmentProductAdapter.setData(installmentProducts)
            binding.txtTotalValueAddInstallmentProduct.text = "Total value: ${installmentProducts.sumOf { it.amount }}$"
            dialogEditInstallmentProduct.dismiss()
        }

        dialogBinding.btnCancelAddInstallmentProduct.setOnClickListener {
            dialogEditInstallmentProduct.dismiss()
        }

        dialogEditInstallmentProduct.setView(dialogBinding.root)

        dialogEditInstallmentProduct.show()
    }

    override fun onRemoveClick(installmentProduct: InstallmentProduct) {
        installmentProducts.remove(installmentProduct)

        installmentProductAdapter.setData(installmentProducts)
    }
}