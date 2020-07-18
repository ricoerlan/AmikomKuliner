package com.example.nearby_resto.ui.home

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_resto.R
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.adapter.DataRestoAdapter
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.databinding.ActivityHomeBinding
import com.example.nearby_resto.ui.main.CallFragment
import com.example.nearby_resto.ui.main.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private var myCompositeDisposable: CompositeDisposable? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var restoRepo: RestoRepository

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_call -> {
                val fragment = CallFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                val fragment = SearchFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val binding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = CallFragment()
        addFragment(fragment)

        myCompositeDisposable = CompositeDisposable()

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