package com.example.nearby_resto.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nearby_resto.R
import com.example.nearby_resto.databinding.ActivityHomeBinding
import com.example.nearby_resto.ui.auth.AuthViewModel
import com.example.nearby_resto.ui.main.*
import com.example.nearby_resto.utils.GpsUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import java.util.*


const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101
class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
//    private val factory: HomeViewModelFactory by instance()
    private var myCompositeDisposable: CompositeDisposable? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var mAuth : AuthViewModel
    private lateinit var binding: ActivityHomeBinding
    private var isGPSEnabled = false

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_call -> {
                val fragment = HomeFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                val fragment = SearchFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qrcode_scanner -> {
                val fragment = QRcodeScannerFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_transaction_history -> {
                val fragment = TransactionHistoryFragment()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val fragment = ProfileFragment()
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewmodel = viewModel

        GpsUtils(this).turnGPSOn(object : GpsUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
                this@HomeActivity.isGPSEnabled = isGPSEnable
            }
        })

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val fragment = HomeFragment()
        addFragment(fragment)

        myCompositeDisposable = CompositeDisposable()

    }

    public override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }


    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> Toast.makeText(this, R.string.enable_gps,Toast.LENGTH_LONG).show()

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() ->
                Toast.makeText(this, R.string.permission_request,Toast.LENGTH_LONG).show()

            else -> ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(this, Observer {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            val subKota: String? = addresses[0].subLocality
            val kota: String? = addresses[0].locality

            if(subKota!!.isNotEmpty() && kota!!.isNotEmpty()){
                binding.toolbar.title = "$subKota, $kota"
            }
//             Toast.makeText(this, "latitude :" + it.latitude + "  longitude:" + it.longitude ,Toast.LENGTH_LONG).show()
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
}

