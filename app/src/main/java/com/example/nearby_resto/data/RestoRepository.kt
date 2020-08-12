package com.example.nearby_resto.data

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.data.model.DataTransaksi
import com.example.nearby_resto.data.model.KategoriResto
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestoRepository: ViewModel() {

    private val _dataResto = MutableLiveData<List<DataResto>>()
    val dataResto : LiveData<List<DataResto>>
        get() = _dataResto

    private val _dataKategori = MutableLiveData<List<KategoriResto>>()
    val dataKategori : LiveData<List<KategoriResto>>
        get() = _dataKategori

    private val _dataTransaksi = MutableLiveData<List<DataTransaksi>>()
    val dataTransaksi : LiveData<List<DataTransaksi>>
        get() = _dataTransaksi

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    val user = FirebaseAuth.getInstance().currentUser

    init {
        _response.value = ""
        initDataKategori()
        initDataResto()
        initDataTransaksi()
    }

    fun initDataKategori(){
        uiScope.launch {
            try {
                val result = ApiServices.ApiResto.retrofitService.getAllCategories()

                if (result.isNotEmpty()){
                    _dataKategori.value = result
                    Log.d("debug","result $result")
                }
                else {
                    _response.value = "$_dataKategori Kosong"
                }
            }catch (t:Throwable){
                _response.value = "Tidak Ada Koneksi Internet"
            }
        }
    }

    fun initDataResto() {
        uiScope.launch {
            try {
                val result = ApiServices.ApiResto.retrofitService.getAllResto()

                if (result.isNotEmpty()) {
                    _dataResto.value = result
                } else {
                    _response.value = "Data Resto kosong!"
                }
            } catch (t: Throwable){
                _response.value = "Tidak ada koneksi internet!"
            }
        }
    }

    fun initDataTransaksi() {
        val email = user?.email.toString()
        uiScope.launch {
            try {
                val result = ApiServices.ApiResto.retrofitService.getTransactionList(email)

                if (result.isNotEmpty()) {
                    _dataTransaksi.value = result
                } else {
                    _response.value = "Data Transaksi kosong!"
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