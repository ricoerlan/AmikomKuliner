package com.example.nearby_resto.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nearby_resto.MyFirebaseInstanceIdService
import com.example.nearby_resto.data.UserRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }

}