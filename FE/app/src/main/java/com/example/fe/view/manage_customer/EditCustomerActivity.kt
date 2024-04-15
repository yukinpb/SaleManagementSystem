package com.example.fe.view.manage_customer

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityEditCustomerBinding
import com.example.fe.model.Customer
import com.example.fe.retrofit.CustomerApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditCustomerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val customer = intent.getSerializableExtra("customer") as Customer

        binding.edtEditCustomerId.setText(customer.id.toString())
        binding.edtEditCustomerName.setText(customer.name)
        binding.edtEditCustomerPhoneNumber.setText(customer.phoneNumber)
        binding.edtEditCustomerEmail.setText(customer.email)
        binding.edtEditCustomerStreetAddress.setText(customer.streetAddress)
        binding.edtEditCustomerCity.setText(customer.city)
        binding.edtEditCustomerCountry.setText(customer.country)
        binding.edtEditCustomerJob.setText(customer.job)
        binding.edtEditCustomerBankCardNumber.setText(customer.bankCardNumber)
        binding.edtEditCustomerIncome.setText(customer.income.toString())
        binding.edtEditCustomerWorkPlace.setText(customer.workPlace)
        binding.edtEditCustomerDob.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(customer.dateOfBirth)
        if(binding.rbEditCustomerMale.text == customer.gender) {
            binding.rbEditCustomerMale.isChecked = true
        }
        else {
            binding.rbEditCustomerFemale.isChecked = true
        }

        binding.btnEditCustomerReset.setOnClickListener {
            binding.edtEditCustomerName.text.clear()
            binding.edtEditCustomerPhoneNumber.text.clear()
            binding.edtEditCustomerEmail.text.clear()
            binding.edtEditCustomerStreetAddress.text.clear()
            binding.edtEditCustomerCity.text.clear()
            binding.edtEditCustomerCountry.text.clear()
            binding.edtEditCustomerJob.text.clear()
            binding.edtEditCustomerBankCardNumber.text.clear()
            binding.edtEditCustomerIncome.text.clear()
            binding.edtEditCustomerWorkPlace.text.clear()
            binding.edtEditCustomerDob.text = ""
            binding.rbEditCustomerMale.isChecked = true
        }

        binding.btnEditCustomerCancel.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        binding.edtEditCustomerName.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.rgEditCustomerGender.requestFocus()
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerDob.setOnClickListener() {
            showDatePickerDialog()
        }

        binding.edtEditCustomerPhoneNumber.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerEmail.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerEmail.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerStreetAddress.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerStreetAddress.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerCity.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerCity.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerCountry.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerCountry.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerJob.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerJob.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerBankCardNumber.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerBankCardNumber.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerWorkPlace.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerWorkPlace.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtEditCustomerIncome.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtEditCustomerIncome.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.btnEditCustomerConfirm.setOnClickListener {
            when {
                binding.edtEditCustomerName.text.isEmpty() -> {
                    binding.edtEditCustomerName.requestFocus()
                    binding.edtEditCustomerName.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerPhoneNumber.text.isEmpty() -> {
                    binding.edtEditCustomerPhoneNumber.requestFocus()
                    binding.edtEditCustomerPhoneNumber.backgroundTintList = ColorStateList.valueOf(
                        Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerEmail.text.isEmpty() -> {
                    binding.edtEditCustomerEmail.requestFocus()
                    binding.edtEditCustomerEmail.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerStreetAddress.text.isEmpty() -> {
                    binding.edtEditCustomerStreetAddress.requestFocus()
                    binding.edtEditCustomerStreetAddress.backgroundTintList = ColorStateList.valueOf(
                        Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerCity.text.isEmpty() -> {
                    binding.edtEditCustomerCity.requestFocus()
                    binding.edtEditCustomerCity.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerCountry.text.isEmpty() -> {
                    binding.edtEditCustomerCountry.requestFocus()
                    binding.edtEditCustomerCountry.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerJob.text.isEmpty() -> {
                    binding.edtEditCustomerJob.requestFocus()
                    binding.edtEditCustomerJob.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerBankCardNumber.text.isEmpty() -> {
                    binding.edtEditCustomerBankCardNumber.requestFocus()
                    binding.edtEditCustomerBankCardNumber.backgroundTintList = ColorStateList.valueOf(
                        Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerIncome.text.isEmpty() -> {
                    binding.edtEditCustomerIncome.requestFocus()
                    binding.edtEditCustomerIncome.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerWorkPlace.text.isEmpty() -> {
                    binding.edtEditCustomerWorkPlace.requestFocus()
                    binding.edtEditCustomerWorkPlace.backgroundTintList = ColorStateList.valueOf(
                        Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerDob.text.isEmpty() -> {
                    binding.edtEditCustomerDob.requestFocus()
                    binding.edtEditCustomerDob.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                !isValidEmail(binding.edtEditCustomerEmail.text.toString()) -> {
                    binding.edtEditCustomerEmail.requestFocus()
                    binding.edtEditCustomerEmail.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                }
                !isValidPhoneNumber(binding.edtEditCustomerPhoneNumber.text.toString()) -> {
                    binding.edtEditCustomerPhoneNumber.requestFocus()
                    binding.edtEditCustomerPhoneNumber.backgroundTintList = ColorStateList.valueOf(
                        Color.RED)
                    Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                }
                binding.edtEditCustomerIncome.text.toString().toDouble() <= 0 -> {
                    binding.edtEditCustomerIncome.requestFocus()
                    binding.edtEditCustomerIncome.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Income must be greater than 0", Toast.LENGTH_SHORT).show()
                }
                !isDateValid(binding.edtEditCustomerDob.text.toString()) -> {
                    binding.edtEditCustomerDob.requestFocus()
                    binding.edtEditCustomerDob.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Customer must be at least 18 years old", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    lifecycleScope.launch {
                        // Save the customer
                        customer.job = binding.edtEditCustomerJob.text.toString()
                        customer.bankCardNumber = binding.edtEditCustomerBankCardNumber.text.toString()
                        customer.income = binding.edtEditCustomerIncome.text.toString().toDouble()
                        customer.workPlace = binding.edtEditCustomerWorkPlace.text.toString()
                        customer.name = binding.edtEditCustomerName.text.toString()
                        customer.phoneNumber = binding.edtEditCustomerPhoneNumber.text.toString()
                        customer.email = binding.edtEditCustomerEmail.text.toString()
                        customer.streetAddress = binding.edtEditCustomerStreetAddress.text.toString()
                        customer.city = binding.edtEditCustomerCity.text.toString()
                        customer.country = binding.edtEditCustomerCountry.text.toString()
                        customer.dateOfBirth = java.sql.Date(stringToDate(binding.edtEditCustomerDob.text.toString(), "dd/MM/yyyy")!!.time)
                        if(binding.rbEditCustomerMale.isChecked) {
                            customer.gender = binding.rbEditCustomerMale.text.toString()
                        }
                        else {
                            customer.gender = binding.rbEditCustomerFemale.text.toString()
                        }
                        CustomerApi.retrofitService.editCustomer(customer)
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

        if (binding.edtEditCustomerDob.text.isNotEmpty()) {
            val date = sdf.parse(binding.edtEditCustomerDob.text.toString())
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
                binding.edtEditCustomerDob.text = selectedDate
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