package com.example.nearby_resto.ui.main.detail

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.utils.CartUtils
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.ApiServices.ApiResto.BASE_URL
import com.example.nearby_resto.data.ApiServices.ApiService
import com.example.nearby_resto.data.adapter.DataMenuScanAdapter
import com.example.nearby_resto.data.model.DataMenu
import com.example.nearby_resto.databinding.ActivityScanCodeDetailRestoBinding
import com.example.nearby_resto.ui.home.HomeViewModel
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_scan_code_detail_resto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ScanCodeDetailResto : AppCompatActivity() {

    lateinit var binding: ActivityScanCodeDetailRestoBinding
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan_code_detail_resto)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val nama_resto: String? = intent.getStringExtra("nama_resto")

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val collapsingToolbarLayout = binding.collapsingToolbar
        collapsingToolbarLayout.title = nama_resto

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.white))
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.white))
//        binding.swipeContainer.isRefreshing = true

        Paper.init(this)
        binding.cartSize.text = CartUtils.getShoppingCartSize().toString()

        loadData()
        startLocationUpdate()

        showCart.setOnClickListener {
            startActivity(Intent(this, ShoppingCartActivity::class.java))
        }

        binding.BTNGmaps.setOnClickListener(){
            goToGmaps()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val cart = CartUtils.getCart()
        CartUtils.clearCart(cart)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        val cart = CartUtils.getCart()
        CartUtils.clearCart(cart)
        return super.onSupportNavigateUp()
    }

    private fun loadData() {

        val id_resto: Int? = intent.getIntExtra("id_resto", 1)
        val nama_resto: String? = intent.getStringExtra("nama_resto")
        val lokasi_resto: String? = intent.getStringExtra("lokasi_resto")
        val photo_resto: String? = intent.getStringExtra("photo_resto")

        val apiService: ApiService = ApiServices.ApiResto.retrofitService
        val call: Call<List<DataMenu>> = apiService.showMenu(id_resto)

        call.enqueue(object : Callback<List<DataMenu>> {
            override fun onResponse(
                call: Call<List<DataMenu>>,
                response: Response<List<DataMenu>>
            ) {
                if (response.isSuccessful) {
                    val list: List<DataMenu>? = response.body()
                    val adapter = list?.let { DataMenuScanAdapter(it) }
                    val recyclerView = binding.recyclerViewMenu
                    val URL_FOTO: String = BASE_URL + "TableResto/" + photo_resto

                    binding.tvLokasiResto.text = lokasi_resto
                    binding.tvNamaResto.text = nama_resto

                    Glide.with(this@ScanCodeDetailResto)
                        .load(URL_FOTO)
                        .placeholder(R.drawable.ic_launcher_background)
                        .override(600,1100)
                        .into(binding.viewImgDetail)


                    recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
                    recyclerView.adapter = adapter

                }
            }

            override fun onFailure(
                call: Call<List<DataMenu>>,
                t: Throwable
            ) {
                Toast.makeText(this@ScanCodeDetailResto, "Gagal Fetch Data" + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun goToGmaps(){
        val lokasi_resto: String? = intent.getStringExtra("lokasi_resto")
        val gmmIntentUri =
            Uri.parse("google.navigation:q=" + lokasi_resto)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(this, Observer {
            val latitude_resto: Double = intent.getDoubleExtra("latitude_resto", it.latitude)
            val longitude_resto: Double = intent.getDoubleExtra("longitude_resto", it.longitude)

            val locationA = Location("point A")

            locationA.latitude = it.latitude
            locationA.longitude = it.longitude

            val locationB = Location("point B")

            locationB.latitude = latitude_resto
            locationB.longitude = longitude_resto

            val distance: Float = locationA.distanceTo(locationB)/1000

            binding.tvLokasiKeResto.text = distance.toString()
        })
    }

}