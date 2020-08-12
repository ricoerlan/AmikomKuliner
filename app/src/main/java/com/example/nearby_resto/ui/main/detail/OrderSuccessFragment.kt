package com.example.nearby_resto.ui.main.detail

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.example.nearby_resto.R
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.ui.home.HomeActivity
import com.example.nearby_resto.ui.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.order_success_fragment.*
import kotlinx.android.synthetic.main.order_success_fragment.view.*

class OrderSuccessFragment : Fragment() {

    private val  TAG = OrderSuccessFragment::class.java.simpleName
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = inflater.inflate(R.layout.order_success_fragment, container, false)

        layout.btn_home.setOnClickListener {

            val intent = Intent(context, HomeActivity::class.java)

            // method startActivity cma bisa di pake di activity/fragment
            // jadi harus masuk ke context dulu
            startActivity(intent)
        }

        return layout
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

    }

}
