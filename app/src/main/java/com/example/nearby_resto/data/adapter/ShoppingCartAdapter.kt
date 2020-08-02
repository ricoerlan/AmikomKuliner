package com.example.nearby_resto.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.data.model.CartItem
import kotlinx.android.synthetic.main.cart_list.view.*
import kotlinx.android.synthetic.main.list_menu.view.*

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        // The layout design used for each list item
        val layout = LayoutInflater.from(context).inflate(R.layout.cart_list, parent, false)

        return ViewHolder(layout)
    }

    // This returns the size of the list.
    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        //we simply call the `bindItem` function here
        viewHolder.bindItem(cartItems[position])
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(cartItem: CartItem) {

            val IMAGE_URL: String = "https://hipmagazine.000webhostapp.com/TableMenu/images/"

            itemView.product_name.text = cartItem.product.nama_menu

            itemView.product_price.text = "${cartItem.product.harga_menu}"

            Glide.with(itemView.context)
                .load(IMAGE_URL + cartItem.product.foto_menu)
                .placeholder(R.drawable.ic_launcher_background)
                .override(300, 750)
                .into(itemView.product_image)

            itemView.product_quantity.text = cartItem.quantity.toString()

        }
    }

}