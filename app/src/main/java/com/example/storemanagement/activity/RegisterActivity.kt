package com.example.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.storemanagement.R
import com.example.storemanagement.data.request.RegisterRequest
import com.example.storemanagement.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity:BaseActivity() {
    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,RegisterActivity::class.java)
        }
    }

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerViewModel= RegisterViewModel()

        val name=etName.text.toString()
        val password=etPassword.text.toString()
        val confirmPassword=etConfirmPassword.text.toString()

        btnRegister.setOnClickListener {
                val req=RegisterRequest(
                        name = name,
                        password = password,
                        deviceToken = "Sample Device Token"
                )
                registerViewModel.registerUser(req)

        }

        registerViewModel.responseMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        registerViewModel.isLoading.observe(this, Observer {
            if (it){
                //todo: show loading
            }else{
                //todo: hide loading
            }
        })

        registerViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }
}