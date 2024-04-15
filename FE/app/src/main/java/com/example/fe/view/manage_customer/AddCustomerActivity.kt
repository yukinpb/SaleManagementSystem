package com.example.fe.view.manage_customer

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityAddCustomerBinding
import com.example.fe.designpattern.builder.InstallmentContractBuilder
import com.example.fe.model.Customer
import com.example.fe.model.User
import com.example.fe.retrofit.CustomerApi
import com.example.fe.view.sign_installment_contract.AddInstallmentProductActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddCustomerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCustomerBinding
    private var from: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        from = intent.getStringExtra("from").toString()

        binding.btnAddCustomerReset.setOnClickListener {
            binding.edtAddCustomerName.text.clear()
            binding.edtAddCustomerPhoneNumber.text.clear()
            binding.edtAddCustomerEmail.text.clear()
            binding.edtAddCustomerStreetAddress.text.clear()
            binding.edtAddCustomerCity.text.clear()
            binding.edtAddCustomerCountry.text.clear()
            binding.edtAddCustomerJob.text.clear()
            binding.edtAddCustomerBankCardNumber.text.clear()
            binding.edtAddCustomerIncome.text.clear()
            binding.edtAddCustomerWorkPlace.text.clear()
            binding.edtAddCustomerDob.text = ""
            binding.rbAddCustomerMale.isChecked = true
        }

        binding.btnAddCustomerCancel.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        binding.edtAddCustomerName.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.rgAddCustomerGender.requestFocus()
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerDob.setOnClickListener() {
            showDatePickerDialog()
        }

        binding.edtAddCustomerPhoneNumber.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerEmail.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerEmail.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerStreetAddress.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerStreetAddress.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerCity.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerCity.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerCountry.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerCountry.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerJob.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerJob.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerBankCardNumber.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerBankCardNumber.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerWorkPlace.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerWorkPlace.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtAddCustomerIncome.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtAddCustomerIncome.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.btnAddCustomerConfirm.setOnClickListener {
    when {
        binding.edtAddCustomerName.text.isEmpty() -> {
            binding.edtAddCustomerName.requestFocus()
            binding.edtAddCustomerName.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerPhoneNumber.text.isEmpty() -> {
            binding.edtAddCustomerPhoneNumber.requestFocus()
            binding.edtAddCustomerPhoneNumber.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerEmail.text.isEmpty() -> {
            binding.edtAddCustomerEmail.requestFocus()
            binding.edtAddCustomerEmail.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerStreetAddress.text.isEmpty() -> {
            binding.edtAddCustomerStreetAddress.requestFocus()
            binding.edtAddCustomerStreetAddress.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerCity.text.isEmpty() -> {
            binding.edtAddCustomerCity.requestFocus()
            binding.edtAddCustomerCity.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerCountry.text.isEmpty() -> {
            binding.edtAddCustomerCountry.requestFocus()
            binding.edtAddCustomerCountry.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerJob.text.isEmpty() -> {
            binding.edtAddCustomerJob.requestFocus()
            binding.edtAddCustomerJob.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerBankCardNumber.text.isEmpty() -> {
            binding.edtAddCustomerBankCardNumber.requestFocus()
            binding.edtAddCustomerBankCardNumber.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerIncome.text.isEmpty() -> {
            binding.edtAddCustomerIncome.requestFocus()
            binding.edtAddCustomerIncome.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerWorkPlace.text.isEmpty() -> {
            binding.edtAddCustomerWorkPlace.requestFocus()
            binding.edtAddCustomerWorkPlace.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerDob.text.isEmpty() -> {
            binding.edtAddCustomerDob.requestFocus()
            binding.edtAddCustomerDob.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
        !isValidEmail(binding.edtAddCustomerEmail.text.toString()) -> {
            binding.edtAddCustomerEmail.requestFocus()
            binding.edtAddCustomerEmail.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
        }
        !isValidPhoneNumber(binding.edtAddCustomerPhoneNumber.text.toString()) -> {
            binding.edtAddCustomerPhoneNumber.requestFocus()
            binding.edtAddCustomerPhoneNumber.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
        }
        binding.edtAddCustomerIncome.text.toString().toDouble() <= 0 -> {
            binding.edtAddCustomerIncome.requestFocus()
            binding.edtAddCustomerIncome.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Income must be greater than 0", Toast.LENGTH_SHORT).show()
        }
        !isDateValid(binding.edtAddCustomerDob.text.toString()) -> {
            binding.edtAddCustomerDob.requestFocus()
            binding.edtAddCustomerDob.backgroundTintList = ColorStateList.valueOf(Color.RED)
            Toast.makeText(this, "Customer must be at least 18 years old", Toast.LENGTH_SHORT).show()
        }
        else -> {
            lifecycleScope.launch {
                // Save the customer
                val customer = Customer(
                    job = binding.edtAddCustomerJob.text.toString(),
                    bankCardNumber = binding.edtAddCustomerBankCardNumber.text.toString(),
                    income = binding.edtAddCustomerIncome.text.toString().toDouble(),
                    workPlace = binding.edtAddCustomerWorkPlace.text.toString(),
                )
                customer.name = binding.edtAddCustomerName.text.toString()
                customer.phoneNumber = binding.edtAddCustomerPhoneNumber.text.toString()
                customer.email = binding.edtAddCustomerEmail.text.toString()
                customer.streetAddress = binding.edtAddCustomerStreetAddress.text.toString()
                customer.city = binding.edtAddCustomerCity.text.toString()
                customer.country = binding.edtAddCustomerCountry.text.toString()
                customer.dateOfBirth = java.sql.Date(stringToDate(binding.edtAddCustomerDob.text.toString(), "dd/MM/yyyy")!!.time)
                if(binding.rbAddCustomerMale.isChecked) {
                    customer.gender = binding.rbAddCustomerMale.text.toString()
                }
                else {
                    customer.gender = binding.rbAddCustomerFemale.text.toString()
                }
                val customerResponse = CustomerApi.retrofitService.addCustomer(customer)

                if(from == "search_customer") {
                    setResult(RESULT_OK)
                    finish()
                }
                else {
                    if(customerResponse != null) {
                        val user = intent.getSerializableExtra("user") as User
                        val intent = Intent(this@AddCustomerActivity, AddInstallmentProductActivity::class.java)
                        val installmentContract = InstallmentContractBuilder()
                            .setInstallmentCustomer(customerResponse)
                            .setCreater(user!!)
                            .build()
                        intent.putExtra("installmentContract", installmentContract)
                        startActivity(intent)
                    }
                }
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

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})$"
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberRegex = "^[0-9]{10}$"
        return phoneNumber.matches(phoneNumberRegex.toRegex())
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        if (binding.edtAddCustomerDob.text.isNotEmpty()) {
            val date = sdf.parse(binding.edtAddCustomerDob.text.toString())
            date?.let {
                calendar.time = it
            }
        }

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/$selectedYear"
                binding.edtAddCustomerDob.text = selectedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun isDateValid(dateString: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dob = sdf.parse(dateString)
        val currentDate = Calendar.getInstance()

        if (dob != null) {
            val dobCalendar = Calendar.getInstance()
            dobCalendar.time = dob

            val yearsBetween = currentDate.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)
            if (yearsBetween > 18) {
                return true
            } else if (yearsBetween == 18) {
                if (currentDate.get(Calendar.DAY_OF_YEAR) >= dobCalendar.get(Calendar.DAY_OF_YEAR)) {
                    return true
                }
            }
        }
        return false
    }

    private fun stringToDate(dateString: String, format: String): Date? {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.parse(dateString)
    }
}