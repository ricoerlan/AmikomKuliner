package com.example.nearby_resto.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.nearby_resto.R
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.UserRepository
import com.example.nearby_resto.ui.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.view.*

class ProfileFragment : Fragment() {

    private val  TAG = ProfileFragment::class.java.simpleName
    private lateinit var viewModel: MainViewModel
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = inflater.inflate(R.layout.profile_fragment, container, false)

        Log.d(TAG, "onCreateView: useremail = ${user?.email} name = ${user?.displayName} userUid = ${user?.uid} ")

        layout.tv_email_profile.text = user?.email

        return layout
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        btn_logout.setOnClickListener {
            viewModel.logout()
            goToLogin()
        }

    }

    fun goToLogin(){
        Intent(context,LoginActivity::class.java).also {
            startActivity(it)
        }
    }

}
