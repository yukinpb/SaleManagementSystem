package com.example.fe.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.fe.databinding.ActivityLoginBinding
import com.example.fe.retrofit.UserApi
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadLoginInfo()

        binding.edtUsername.setOnEditorActionListener() { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.edtPassword.requestFocus()
                true
            }
            else {
                false
            }
        }

        binding.edtPassword.setOnEditorActionListener() { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                binding.btnLogin.performClick()
                true
            }
            else {
                false
            }
        }

        binding.btnLogin.setOnClickListener {
            lifecycleScope.launch {
                val user = UserApi.retrofitService.login(binding.edtUsername.text.toString(), binding.edtPassword.text.toString())
                if(user != null) {
                    // Login success
                    saveLoginInfo(binding.edtUsername.text.toString(), binding.edtPassword.text.toString())
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("user", user)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    // Login failed
                    Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveLoginInfo(username: String, password: String) {
        val sharedPref = getSharedPreferences("LoginInfo", MODE_PRIVATE)
        with (sharedPref.edit()) {
            putString("username", username)
            putString("password", password)
            apply()
        }
    }

    private fun loadLoginInfo() {
        val sharedPref = getSharedPreferences("LoginInfo", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        binding.edtUsername.setText(username)
        binding.edtPassword.setText(password)
    }
}