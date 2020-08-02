package com.example.nearby_resto.ui.main.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nearby_resto.R
import com.example.nearby_resto.utils.CartUtils
import com.example.nearby_resto.data.adapter.ShoppingCartAdapter
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var adapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ShoppingCartAdapter(this, CartUtils.getCart())
        adapter.notifyDataSetChanged()

        shopping_cart_recyclerView.adapter = adapter

        shopping_cart_recyclerView.layoutManager = LinearLayoutManager(this)

        var totalPrice = CartUtils.getCart()
            .fold(0.toDouble()) { acc, cartItem -> acc + cartItem.quantity.times(cartItem.product.harga_menu!!.toDouble()) }

        total_price.text = "$totalPrice"
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
}