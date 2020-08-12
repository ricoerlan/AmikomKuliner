package com.example.nearby_resto.ui.main.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.nearby_resto.R
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.model.DataTransaksi
import com.example.nearby_resto.databinding.ActivityResultCheckoutBinding
import kotlinx.android.synthetic.main.activity_result_checkout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCheckoutActivity : AppCompatActivity() {

    val TAG = ResultCheckoutActivity::class.java.simpleName
    private lateinit var binding: ActivityResultCheckoutBinding
    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_checkout)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        binding.imgCheckoutSuccess.visibility = View.GONE
        binding.imgCheckoutFailed.visibility = View.GONE

        val value: String? = intent.getStringExtra("value_data")

        if (value == "1"){
            binding.imgCheckoutSuccess.visibility = View.VISIBLE
            Log.d(TAG, "isi value = $value")
        }else{
            pesananGagal()
            Log.d(TAG, "isi value = $value")
        }

        binding.btnKonfirmasiPembayaran.setOnClickListener {
            val id_pesanan: Int = intent.getIntExtra("id_pesanan",1)
            val nama_pemesan: String? = intent.getStringExtra("nama_pemesan")
            Log.d(TAG, "onCreate: isi id_pesanan = $id_pesanan")

            viewModel.checkTransaction(id_pesanan)
            viewModel.trxCheckResult.observeForever {
                val status_transaksi = it[0].status_transksi

                if (status_transaksi == "Pending") {
                    Log.d(TAG, "onResponse: $status_transaksi")
                    Toast.makeText(
                        this@ResultCheckoutActivity,
                        "Pembayaran tidak dapat Dikonfirmasi , Mohon ke bagian Kasir untuk melakukan Pembayaran",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Log.d(TAG, "onResponse: $status_transaksi")
                    Toast.makeText(
                        this@ResultCheckoutActivity,
                        "Terima Kasih , Pembayaran telah dikonfirmasi pesanan atas nama $nama_pemesan akan segera kami proses",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.btnOrderDetail.visibility = View.GONE
                    binding.tvCheckout.visibility = View.GONE
                    binding.tvTombolKonfirmasi.visibility = View.GONE
                    binding.btnKonfirmasiPembayaran.visibility = View.GONE
                    binding.btnKonfirmasiPembayaran.visibility = View.GONE

                    val fragment = OrderSuccessFragment()
                    addFragment(fragment)
                }
            }

//            val call: Call<List<DataTransaksi>> = ApiServices.ApiResto.retrofitService.checkTransaction(id_pesanan)
//
//            call.enqueue(object : Callback<List<DataTransaksi>> {
//                override fun onResponse(
//                    call: Call<List<DataTransaksi>>,
//                    response: Response<List<DataTransaksi>>
//                ) {
//                    val DataTransaksi : List<DataTransaksi>? = response.body()
//                    val status_transaksi : String? = DataTransaksi?.get(0)?.status_transksi
//
//                    if (status_transaksi == "Pending") {
//                        Log.d(TAG, "onResponse: $status_transaksi")
//                        Toast.makeText(this@ResultCheckoutActivity,
//                            "Pembayaran tidak dapat Dikonfirmasi , Mohon ke bagian Kasir untuk melakukan Pembayaran",
//                            Toast.LENGTH_SHORT).show()
//
//                    } else {
//                        Log.d(TAG, "onResponse: $status_transaksi")
//                        Toast.makeText(this@ResultCheckoutActivity,
//                            "Terima Kasih , Pembayaran telah dikonfirmasi pesanan atas nama $nama_pemesan akan segera kami proses",
//                            Toast.LENGTH_SHORT).show()
//
//                        binding.btnOrderDetail.visibility = View.GONE
//                        binding.tvCheckout.visibility = View.GONE
//                        binding.tvTombolKonfirmasi.visibility = View.GONE
//                        binding.btnKonfirmasiPembayaran.visibility = View.GONE
//                        binding.btnKonfirmasiPembayaran.visibility = View.GONE
//
//                        val fragment = OrderSuccessFragment()
//                       addFragment(fragment)
//
//                    }
//                }
//
//                override fun onFailure(call: Call<List<DataTransaksi>>, t: Throwable) {
//                    Log.d(TAG, "onFailure: Jaringan error")
//                    Toast.makeText(this@ResultCheckoutActivity, "Jaringan Error! Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show()
//                }
//            })

        }

    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment, fragment.javaClass.getSimpleName())
            .commit()
    }

    @SuppressLint("SetTextI18n")
    fun pesananGagal(){
        tv_checkout.text = "Pesananmu gagal diproses silahkan cek kembali Data Anda"
        img_checkout_failed.visibility = View.VISIBLE
        tv_tombol_konfirmasi.visibility = View.GONE
        btn_konfirmasi_pembayaran.visibility = View.GONE
    }



}