package com.example.nearby_resto.ui.main


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.nearby_resto.R
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.ui.main.detail.ScanCodeDetailResto
import com.example.nearby_resto.utils.CustomViewFinderView
import com.google.zxing.Result
import kotlinx.android.synthetic.main.qrcode_scanner.*
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zxing.ZXingScannerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QRcodeScannerFragment : Fragment(), ZXingScannerView.ResultHandler, View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var mScannerView: ZXingScannerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout = inflater.inflate(R.layout.qrcode_scanner, container, false)


        return layout
        // TODO: Use the ViewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initScannerView()
        initDefaultView()
        button_reset.setOnClickListener(this)

    }

    private fun initScannerView() {
        mScannerView = object : ZXingScannerView(context) {
            override fun createViewFinderView(context: Context?): IViewFinder {
                return CustomViewFinderView(context!!)
            }
        }
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        frame_layout_camera.addView(mScannerView)
    }

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.let { checkSelfPermission(it,Manifest.permission.CAMERA) } != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    private fun initDefaultView() {
        text_view_qr_code_value.text = "QR Code Value"
        button_reset.visibility = View.GONE
    }

    override fun handleResult(rawResult: Result?) {
        text_view_qr_code_value.text = rawResult?.text
        val id_resto = rawResult?.text?.toInt()

        val apiService: ApiServices.ApiService = ApiServices.ApiResto.retrofitService
        val call: Call<List<DataResto>> = apiService.showScannedResto(id_resto)

        call.enqueue(object : Callback<List<DataResto>> {
            override fun onResponse(
                call: Call<List<DataResto>>,
                response: Response<List<DataResto>>
            ) {
                if (response.isSuccessful) {
                    val list: List<DataResto>? = response.body()
                    if (list != null) {

                        val intent = Intent(activity, ScanCodeDetailResto::class.java)

                        intent.putExtra("id_resto", id_resto)
                        intent.putExtra("nama_resto", list[0].nama)
                        intent.putExtra("lokasi_resto", list[0].lokasi)
                        intent.putExtra("photo_resto", list[0].photo)
                        intent.putExtra("latitude_resto", list[0].latitude)
                        intent.putExtra("longitude_resto", list[0].longitude)
                        intent.putExtra("qr_result", rawResult?.text)

                        activity?.startActivity(intent)

                    }

                }
            }

            override fun onFailure(
                call: Call<List<DataResto>>,
                t: Throwable
            ) {
                Toast.makeText(activity, "Gagal Fetch Data" + t.message, Toast.LENGTH_SHORT).show()
            }
        })

//        button_reset.visibility = View.VISIBLE
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.button_reset -> {
                mScannerView.resumeCameraPreview(this)
                initDefaultView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

}
