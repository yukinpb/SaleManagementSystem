package com.example.fe.view.sign_installment_contract

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
import com.example.fe.databinding.ActivityAddInstallmentContractBinding
import com.example.fe.model.InstallmentContract
import com.example.fe.model.toDto
import com.example.fe.retrofit.InstallmentContractApi
import com.example.fe.view.MainActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddInstallmentContractActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddInstallmentContractBinding
    private var installmentContract = InstallmentContract()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddInstallmentContractBinding.inflate(layoutInflater)
        setContentView(binding.root)

        installmentContract = intent.getSerializableExtra("installmentContract") as InstallmentContract

        binding.btnAddInstallmentContractReset.setOnClickListener {
            binding.txtFirstDatePaid.text = ""
            binding.edtDayPaidEachMonth.text.clear()
            binding.edtMaximumDayOutstanding.text.clear()
            binding.edtPenaltyRate.text.clear()
        }

        binding.btnAddInstallmentContractCancel.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        binding.txtFirstDatePaid.setOnClickListener() {
            showDatePickerDialog()
        }


        binding.edtDayPaidEachMonth.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtMaximumDayOutstanding.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtMaximumDayOutstanding.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtPenaltyRate.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtPenaltyRate.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                true
            }
            else {
                false
            }
        }

        binding.btnAddInstallmentContractConfirm.setOnClickListener {
            when {
                binding.edtDayPaidEachMonth.text.isEmpty() -> {
                    binding.edtDayPaidEachMonth.requestFocus()
                    binding.edtDayPaidEachMonth.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtMaximumDayOutstanding.text.isEmpty() -> {
                    binding.edtMaximumDayOutstanding.requestFocus()
                    binding.edtMaximumDayOutstanding.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.edtPenaltyRate.text.isEmpty() -> {
                    binding.edtMaximumDayOutstanding.requestFocus()
                    binding.edtMaximumDayOutstanding.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                binding.txtFirstDatePaid.text.isEmpty() -> {
                    binding.txtFirstDatePaid.requestFocus()
                    binding.txtFirstDatePaid.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
                !isFirstDayPaidValid() -> {
                    binding.txtFirstDatePaid.requestFocus()
                    binding.txtFirstDatePaid.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "First day paid cannot be in the future", Toast.LENGTH_SHORT).show()
                }
                !isDayOfMonth(binding.edtDayPaidEachMonth.text.toString()) -> {
                    binding.edtDayPaidEachMonth.requestFocus()
                    binding.edtDayPaidEachMonth.backgroundTintList = ColorStateList.valueOf(Color.RED)
                    Toast.makeText(this, "Day paid each month must be between 1 and 28", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    lifecycleScope.launch {
                        installmentContract.firstDatePaid = java.sql.Date(stringToDate(binding.txtFirstDatePaid.text.toString(), "dd/MM/yyyy")!!.time)
                        installmentContract.dayPaidEachMonth = binding.edtDayPaidEachMonth.text.toString()
                        installmentContract.maximumDayOutstanding = binding.edtMaximumDayOutstanding.text.toString().toInt()
                        installmentContract.penaltyRate = binding.edtPenaltyRate.text.toString().toDouble()
                        installmentContract.annualInterestRate =
                            installmentContract.lendingPartner?.annualInterestRate ?: 0.0
                        installmentContract.interestRateIncrease =
                            installmentContract.lendingPartner?.interestRateIncrease ?: 0.0
                        val installmentContractDto = installmentContract.toDto()
                        InstallmentContractApi.retrofitService.addInstallmentContract(
                            installmentContractDto
                        )
                        Toast.makeText(this@AddInstallmentContractActivity, "Added successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@AddInstallmentContractActivity, MainActivity::class.java)
                        intent.putExtra("user", installmentContract.user)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
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

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        if (binding.txtFirstDatePaid.text.isNotEmpty()) {
            val date = sdf.parse(binding.txtFirstDatePaid.text.toString())
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
                binding.txtFirstDatePaid.text = selectedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun isFirstDayPaidValid(): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val firstDayPaidDate = sdf.parse(binding.txtFirstDatePaid.text.toString())
        val currentDate = Calendar.getInstance().time

        return (firstDayPaidDate != null && firstDayPaidDate.after(currentDate))
    }

    private fun stringToDate(dateString: String, format: String): Date? {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.parse(dateString)
    }

    private fun isDayOfMonth(day: String): Boolean {
        return day.toIntOrNull()?.let {
            it in 1..28
        } ?: false
    }
}