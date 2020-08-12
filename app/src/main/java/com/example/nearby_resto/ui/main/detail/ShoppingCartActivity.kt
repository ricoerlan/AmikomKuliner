package com.example.nearby_resto.ui.main.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nearby_resto.R
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.utils.CartUtils
import com.example.nearby_resto.data.adapter.ShoppingCartAdapter
import com.example.nearby_resto.data.model.Value
import com.example.nearby_resto.databinding.ActivityShoppingCartBinding
import com.google.firebase.auth.FirebaseAuth
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var adapter: ShoppingCartAdapter
    lateinit var viewModel: ShoppingCartViewModel
    lateinit var binding: ActivityShoppingCartBinding
    val TAG = ShoppingCartActivity::class.java.simpleName
    val user = FirebaseAuth.getInstance().currentUser
    val HomeFragment  = com.example.nearby_resto.ui.main.HomeFragment::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_shopping_cart)

        val viewModel = ViewModelProviders.of(this)[ShoppingCartViewModel::class.java]

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ShoppingCartAdapter(this, CartUtils.getCart())
        adapter.notifyDataSetChanged()

        shopping_cart_recyclerView.adapter = adapter

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(this)

        var totalPrice = CartUtils.getCart()
            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.harga_menu.toDouble()) }

        total_price.text = "$totalPrice"
        btn_checkout.setOnClickListener {
            checkout()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun checkout(){
        Paper.init(this)
        val menu = CartUtils.getCart()[0].toString()
        val no_meja = tv_no_meja.text.toString().toInt()
        val nama_pemesan = tv_nama_pemesan.text.toString()
        val userEmail = user?.email
        val id_pesanan : Int = (menu.length + nama_pemesan.length  + userEmail!!.length).times(3)
        val totalPrice = CartUtils.getCart()
            .fold(0) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.harga_menu) }

        Log.d(TAG, "checkout: menu = $menu ")

        val call: Call<Value> = ApiServices.ApiResto.retrofitService.checkout(
            id_pesanan,no_meja,nama_pemesan,userEmail, menu, totalPrice
        )

        call.enqueue(object : Callback<Value?> {
            override fun onResponse(
                call: Call<Value?>?,
                response: Response<Value?>
            ) {
                val value: String? = response.body()?.value
                val result: String? = response.body()?.result
                if (value == "1") {
                    Log.d(TAG, "onResponse: $result")
                    Toast.makeText(this@ShoppingCartActivity, result, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@ShoppingCartActivity, ResultCheckoutActivity::class.java)

                    intent.putExtra("id_pesanan", id_pesanan)
                    intent.putExtra("nama_pemesan", nama_pemesan)
                    intent.putExtra("value_data" , value)

                    // method startActivity cma bisa di pake di activity/fragment
                    // jadi harus masuk ke context dulu
                    startActivity(intent)

                } else {
                    Log.d(TAG, "onResponse: $result")
                    Toast.makeText(this@ShoppingCartActivity, result, Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@ShoppingCartActivity, ResultCheckoutActivity::class.java)

                    // method startActivity cma bisa di pake di activity/fragment
                    // jadi harus masuk ke context dulu
                    startActivity(intent)

                }
            }

            override fun onFailure(call: Call<Value?>, t: Throwable) {
                Log.d(TAG, "onFailure: Jaringan error")
                Toast.makeText(this@ShoppingCartActivity, "Jaringan Error! Periksa Koneksi Internet Anda", Toast.LENGTH_SHORT).show()
            }
        })
}
    private fun goToSuccessCheckout(){

    }
}