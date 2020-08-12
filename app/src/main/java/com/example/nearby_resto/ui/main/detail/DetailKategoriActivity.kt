package com.example.nearby_resto.ui.main.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.adapter.DataMenuAdapter
import com.example.nearby_resto.data.adapter.DataRestoAdapter
import com.example.nearby_resto.data.model.DataMenu
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.databinding.ActivityDetailKategoriBinding
import com.example.nearby_resto.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailKategoriActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKategoriBinding
    private lateinit var viewModel: DetailViewModel
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_kategori)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        val nama_kategori: String? = intent.getStringExtra("nama_kategori")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val collapsingToolbarLayout = binding.collapsingToolbar
        collapsingToolbarLayout.title = nama_kategori

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.white)
        )

        loadResto()

    }


    private fun loadResto() {
        val id_kategori: Int = intent.getIntExtra("id_kategori", 1)
        val image_kategori: String? = intent.getStringExtra("image_kategori")

        viewModel.getRestoByIdCat(id_kategori)
        viewModel.dataRestoByIdCategory.observeForever {
            val adapter = DataRestoAdapter(it)
            val recyclerView = binding.recyclerViewMenu

            val URL_FOTO: String = ApiServices.ApiResto.BASE_URL + "TableKategori/" + image_kategori

            Glide.with(this@DetailKategoriActivity)
                .load(URL_FOTO)
                .placeholder(R.drawable.gmaps_64px)
                .override(550, 1100)
                .into(binding.viewImgDetailKategori)


            recyclerView.layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            recyclerView.adapter = adapter
        }
    }
}