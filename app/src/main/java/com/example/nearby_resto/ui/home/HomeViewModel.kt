package com.example.nearby_resto.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.nearby_resto.data.LocationLiveData

class HomeViewModel(
        application: Application
) : AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData
}

//    val user by lazy {
//        repository.currentUser()
//    }
//
//    fun logout(view: View){
//        repository.logout()
//        view.context.startLoginActivity()
//    }

