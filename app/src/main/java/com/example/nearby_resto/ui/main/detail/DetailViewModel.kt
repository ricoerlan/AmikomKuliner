package com.example.nearby_resto.ui.main.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.RestoRepository
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.data.model.DataTransaksi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val _dataRestoByIdCategory = MutableLiveData<List<DataResto>>()
    val dataRestoByIdCategory : LiveData<List<DataResto>>
        get() = _dataRestoByIdCategory

    private val _response = MutableLiveData<String>()
    val response : LiveData<String>
        get() = _response

    private val _trxCheckResult = MutableLiveData<List<DataTransaksi>>()
    val trxCheckResult : LiveData<List<DataTransaksi>>
        get() = _trxCheckResult

    private var job = Job()
    private val uiScope = CoroutineScope(job + Dispatchers.Main)

    fun getRestoByIdCat(id_kategori: Int){
        uiScope.launch {
            try {
                val result = ApiServices.ApiResto.retrofitService.GetRestoByIdKategori(id_kategori)

                if (result.isNotEmpty()){
                    _dataRestoByIdCategory.value = result
                    Log.d("debug","result $result")
                }
                else {
                    _response.value = "$_dataRestoByIdCategory Kosong"
                }
            }catch (t:Throwable){
                _response.value = "Tidak Ada Koneksi Internet"
            }
        }
    }

    fun checkTransaction(id_pesanan: Int){
        uiScope.launch {
            try {
                val result = ApiServices.ApiResto.retrofitService.checkTransaction(id_pesanan)

                if (result.isNotEmpty()){
                    _trxCheckResult.value = result
                    Log.d("debug","result $result")
                }
                else {
                    _response.value = "$_trxCheckResult Kosong"
                }
            }catch (t:Throwable){
                _response.value = "Tidak Ada Koneksi Internet"
            }
        }
    }

}
