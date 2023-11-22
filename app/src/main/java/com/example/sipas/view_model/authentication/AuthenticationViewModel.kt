package com.example.sipas.view_model.authentication

import androidx.lifecycle.ViewModel
import com.example.sipas.dao.AuthenticationDao
import com.example.sipas.model.Authentication
import com.example.sipas.model.Response

class AuthenticationViewModel(
    private val authDao: AuthenticationDao
): ViewModel() {

    fun insertAuthenticationInfo(response: Authentication){
        this.authDao.insertAuthInfo(response)
    }
}