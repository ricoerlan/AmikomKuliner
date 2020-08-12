package com.example.nearby_resto.ui.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.adapter.DataRestoAdapter
import com.example.nearby_resto.data.model.DataResto
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel  : ViewModel() {

    private val _data = MutableLiveData<List<DataResto>>()
    val data : LiveData<List<DataResto>>
        get() = _data

    private val _searchData = MutableLiveData<List<DataResto>>()
    val searchData : LiveData<List<DataResto>>
        get() = _searchData

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _response.value = ""
        initData()
    }

    fun initData() {
        uiScope.launch {
            try {
                val result = ApiServices.ApiResto.retrofitService.getAllResto()

                if (result.isNotEmpty()) {
                    _data.value = result
                    Log.d("Retrofit","Berhasil Fetch Data" )
                } else {
                    _response.value = "Data Resto kosong!"
                    Log.d("Retrofit","Data Resto Kosong" )
                }
            } catch (t: Throwable){
                _response.value = "Tidak ada koneksi internet!"
            }
        }
    }

    fun searchResto(text: String) {
        uiScope.launch {
            try {
                val result = ApiServices.ApiResto.retrofitService.search(text)

                if (result != null) {
                    _searchData.value = result
                    Log.d("Retrofit","Berhasil Search Data" )
                } else {
                    _response.value = "Data Resto kosong!"
                    Log.d("Retrofit","Data Resto Kosong" )
                }
            } catch (t: Throwable){
                _response.value = "Tidak ada koneksi internet!"
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}