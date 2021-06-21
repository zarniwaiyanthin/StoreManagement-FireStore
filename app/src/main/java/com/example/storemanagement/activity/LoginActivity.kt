package com.example.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.storemanagement.R
import com.example.storemanagement.data.request.LoginRequest
import com.example.storemanagement.utilities.Constants
import com.example.storemanagement.viewmodel.BaseViewModel
import com.example.storemanagement.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity:BaseActivity() {

    companion object{
        fun newIntent(context: Context):Intent{
            return Intent(context,LoginActivity::class.java)
        }
    }

    private lateinit var loginViewModel:LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel= LoginViewModel()

        btnLogin.setOnClickListener {
            val name=etName.text.toString()
            val password=etPassword.text.toString()

            when{
                name.isBlank()-> Toast.makeText(this, "Please enter user name.", Toast.LENGTH_SHORT).show()
                password.isBlank()-> Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                else->{
                    val req=LoginRequest(
                            userName = name,
                            password = password
                    )
                    loginViewModel.login(req)
                }
            }
        }

        tvRegister.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
            finish()
        }

        loginViewModel.isLoading.observe(this, Observer { isLoading->
            if (isLoading){
                progressBar.visibility= View.VISIBLE
            }else{
                progressBar.visibility=View.INVISIBLE
            }
        })

        loginViewModel.error.observe(this, Observer { error->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        })

        loginViewModel.user.observe(this, Observer { user->
                val pref=getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .putString(Constants.KEY_USER_ID,user)
                    .apply()
                startActivity(MainActivity.newIntent(this))
                finish()
        })
    }
}