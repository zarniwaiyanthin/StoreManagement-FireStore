package com.example.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
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

        btnRegister.setOnClickListener {

            val name=etName.text.toString()
            val password=etPassword.text.toString()
            val confirmPassword=etConfirmPassword.text.toString()

            when{
                name.isBlank()-> Toast.makeText(this, "Please enter user name", Toast.LENGTH_SHORT).show()
                password.isBlank()-> Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                confirmPassword.isBlank()-> Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show()
                password!=confirmPassword-> Toast.makeText(this, "Password and confirm password must be match", Toast.LENGTH_SHORT).show()
                else->{
                    val req=RegisterRequest(
                            name = name,
                            password = password,
                            deviceToken = "Sample Device Token"
                    )
                    registerViewModel.registerUser(req)
                }
            }
        }

        registerViewModel.responseMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        registerViewModel.isLoading.observe(this, Observer {
            if (it){
                progressBar.visibility= View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        registerViewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

    }
}