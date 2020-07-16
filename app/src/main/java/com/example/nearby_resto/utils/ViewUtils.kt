package com.example.nearby_resto.utils

import android.content.Context
import android.content.Intent
import com.example.nearby_resto.ui.auth.LoginActivity
import com.example.nearby_resto.ui.home.HomeActivity

class ViewUtils {

    fun Context.startHomeActivity() =
        Intent(this, HomeActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

    fun Context.startLoginActivity() =
        Intent(this, LoginActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }

}