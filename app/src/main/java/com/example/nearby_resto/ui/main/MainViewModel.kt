package com.example.nearby_resto.ui.main

import androidx.lifecycle.ViewModel
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.UserRepository
import com.example.nearby_resto.data.firebase.FirebaseSource

class MainViewModel : ViewModel() {


    val firebaseSrc = FirebaseSource()
    val restoRepo = RestoRepository()
    val userRepo = UserRepository(firebaseSrc)

    fun getResponse() = restoRepo
    fun getCategories() = restoRepo
    fun getResto() = restoRepo
    fun getTransaction() = restoRepo

    fun logout() = userRepo.logout()


}
