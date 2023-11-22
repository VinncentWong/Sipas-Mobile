package com.example.sipas.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import com.example.sipas.api.OrangtuaApi
import com.example.sipas.database.AuthenticationDatabase
import com.example.sipas.databinding.ActivityLoginBinding
import com.example.sipas.model.Authentication
import com.example.sipas.model.Login
import com.example.sipas.retrofit.Api
import com.example.sipas.util.translateExceptionIntoResponse
import com.example.sipas.view_model.authentication.AuthenticationViewModel
import com.example.sipas.view_model.authentication.AuthenticationViewModelFactory
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var email: TextInputEditText

    private lateinit var password: TextInputEditText

    private lateinit var buttonLogin: MaterialButton

    private lateinit var errorMessage: TextView

    private lateinit var authViewModel: AuthenticationViewModel

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(this.layoutInflater)

        setContentView(binding.root)

        email = binding.editEmail
        password = binding.editPassword
        buttonLogin = binding.loginButton
        errorMessage = binding.errorMessage
        progressBar = binding.progressBar
        authViewModel = AuthenticationViewModelFactory(
            AuthenticationDatabase
                .getInstance(applicationContext)
                .authenticationDao()
        ).create(AuthenticationViewModel::class.java)

        buttonLogin.setOnClickListener {

            runOnUiThread {
                progressBar.visibility = View.VISIBLE
            }

            val coroutineExceptionHandler = CoroutineExceptionHandler{
                _, throwable ->
                val response = translateExceptionIntoResponse(throwable)
                errorMessage.text = response?.message
                runOnUiThread {
                    progressBar.visibility = View.INVISIBLE
                }
            }

            lifecycle.coroutineScope.launch(coroutineExceptionHandler) {
                val response = Api.getInstance()
                    .create(OrangtuaApi::class.java)
                    .loginOrangtua(Login(email = email.text?.toString() ?: "", password = password.text?.toString() ?: ""))

                Log.d("LoginActivity", "accepting response $response")

                authViewModel
                    .insertAuthenticationInfo(Authentication(response.id, response.jwtToken))

                Toast.makeText(applicationContext, "sukses login", Toast.LENGTH_SHORT)

                runOnUiThread {
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}