package com.example.nearby_resto.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.nearby_resto.R
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.adapter.DataTransaksiAdapter
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.data.model.DataTransaksi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.qrcode_scanner.*
import kotlinx.android.synthetic.main.transaction_history_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionHistoryFragment : Fragment() {

    val TAG = this.javaClass.simpleName
    private lateinit var viewModel: MainViewModel
    private lateinit var restoRepo: RestoRepository
    val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = inflater.inflate(R.layout.transaction_history_fragment, container, false)

        return layout
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        loadTransaksi()
    }

    fun loadTransaksi(){
        val recyclerViewTrx = recyclerViewTransaksi
            viewModel.getTransaction().dataTransaksi.observeForever {
                Log.d(TAG, "loadTransaksi: $it")
                recyclerViewTrx.layoutManager = LinearLayoutManager(context,
                    RecyclerView.VERTICAL,false)
                recyclerViewTrx.adapter = DataTransaksiAdapter(it)

            }
        }

    }
