package com.example.nearby_resto.ui.home

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.adapter.DataRestoAdapter
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.databinding.ActivityHomeBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import io.reactivex.disposables.CompositeDisposable
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private var myCompositeDisposable: CompositeDisposable? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var restoRepo: RestoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel

        myCompositeDisposable = CompositeDisposable()

        restoRepo = ViewModelProviders.of(this).get(RestoRepository::class.java)

        restoRepo.data.observe({ lifecycle }, {
            val adapter = DataRestoAdapter(it)
            val recyclerView = binding.recyclerViewResto
            val resto = intent.getParcelableExtra<DataResto>("resto")

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@HomeActivity,RecyclerView.HORIZONTAL,false)


        })

        restoRepo.response.observe({ lifecycle }, {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })

//        // GET MY CURRENT LOCATION
//        val mFusedLocation = LocationServices.getFusedLocationProviderClient(this)
//        mFusedLocation.lastLocation.addOnSuccessListener(this, object : OnSuccessListener<Location> {
//            override fun onSuccess(location: Location?) {
//                // Do it all with location
//                Log.d("My Current location", "Lat : ${location?.latitude} Long : ${location?.longitude}")
//                // Display in Toast
//                Toast.makeText(this@HomeActivity,
//                    "Lat : ${location?.latitude} Long : ${location?.longitude}",
//                    Toast.LENGTH_LONG).show()
//            }
//
//        })

    }

}