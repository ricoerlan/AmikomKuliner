package com.example.nearby_resto.ui.main.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.model.Value
import com.example.nearby_resto.utils.CartUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonArray
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingCartViewModel : ViewModel() {

//    val TAG = ShoppingCartViewModel::class.java.simpleName
//    var nama_pemesan: String? = null
//    var no_meja: String? = null
//    val user = FirebaseAuth.getInstance().currentUser
//
//    var totalPrice = CartUtils.getCart()
//        .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.harga_menu!!.toDouble()) }
//
//    fun checkout(){
//        val cartItem = CartUtils.getCart().toString()
//        val cartItemJson = JSONArray(cartItem)
//        val userEmail = user?.email
//
//        val call: Call<Value> = ApiServices.ApiResto.retrofitService.checkout(
//            no_meja?.toInt(),nama_pemesan,userEmail, cartItem, totalPrice
//        )
//        call.enqueue(object : Callback<Value?> {
//            override fun onResponse(
//                call: Call<Value?>?,
//                response: Response<Value?>
//            ) {
//                val value: String? = response.body()?.value
//                val message: String? = response.body()?.message
//                if (value == "1") {
//                    Log.d(TAG, "onResponse: $message")
////                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.d(TAG, "onResponse: $message")
////                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Value?>, t: Throwable) {
//                Log.d(TAG, "onFailure: Jaringan error")
////                Toast.makeText(this@MainActivity, "Jaringan Error!", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//
//    }

}