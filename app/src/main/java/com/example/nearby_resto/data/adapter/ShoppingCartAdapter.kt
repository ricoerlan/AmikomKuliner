package com.example.nearby_resto.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.data.model.CartItem
import com.example.nearby_resto.ui.main.detail.ScanCodeDetailResto
import com.example.nearby_resto.ui.main.detail.ShoppingCartActivity
import com.example.nearby_resto.utils.CartUtils
import io.paperdb.Paper
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.list_menu.view.*
import kotlinx.android.synthetic.main.list_menu_2.view.*

class ShoppingCartAdapter(var context: Context, var cartItems: List<CartItem>) :
    RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    val IMAGE_URL: String = "https://hipmagazine.000webhostapp.com/TableMenu/images/"

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {

        // The layout design used for each list item
        val layout = LayoutInflater.from(context).inflate(R.layout.list_menu_2, parent, false)

        return ViewHolder(layout)
    }

    // This returns the size of the list.
    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val orderQuantity = cartItems[position].product.quantityOrder

        viewHolder.itemView.txt_food_name.text = cartItems[position].product.nama_menu
        viewHolder.itemView.txt_price.text = "${cartItems[position].product.harga_menu}"


        if (orderQuantity > 0){
            viewHolder.itemView.txt_quantity.text = "${cartItems[position].product.quantityOrder}"
            viewHolder.itemView.txt_quantity.visibility = View.VISIBLE
            viewHolder.itemView.btn_decrement.visibility = View.VISIBLE
        }

        Glide.with(viewHolder.itemView.context)
            .load(IMAGE_URL + cartItems[position].product.foto_menu)
            .placeholder(R.drawable.ic_launcher_background)
            .into(viewHolder.itemView.roundedImageView)

        viewHolder.itemView.txt_quantity.text = cartItems[position].quantity.toString()

        //we simply call the `bindItem` function here
        viewHolder.bindItem(cartItems[position])
    }
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")
        fun bindItem(product: CartItem) {

            val TAG = DataMenuScanAdapter::class.java.simpleName

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {

                view.btn_increment.setOnClickListener { view ->
                    val item = product
                    val activity = itemView.context as Activity
                    val itemSize = CartUtils.getShoppingCartSize().plus(1)
                    Paper.init(activity)

                    CartUtils.addItem(item)
                    val orderQuantity = item.product.quantityOrder

                    Log.d(TAG, "bindProduct: quantity ${item.quantity}")
                    Log.d(TAG, "bindProduct: orderQuantity $orderQuantity")

                    if (orderQuantity == 1) {
                        Toast.makeText(
                            activity,
                            "${item.product.nama_menu} telah ditambahkan ke dalam keranjang",
                            Toast.LENGTH_SHORT
                        ).show()
                        itemView.txt_quantity.text = orderQuantity.toString()
                        itemView.txt_quantity.visibility = View.VISIBLE
                        itemView.btn_decrement.visibility = View.VISIBLE
                    }else if(orderQuantity > 0){
                        itemView.txt_quantity.text = orderQuantity.toString()
                    }

                    Log.d(TAG, "bindViewHolder: ${item.product}")

                    it.onNext(CartUtils.getCart())

                }

                view.btn_decrement.setOnClickListener { view ->
                    val item = product
                    val activity = itemView.context as Activity
                    val itemSize = CartUtils.getShoppingCartSize().minus(1)
                    Paper.init(activity)

                    CartUtils.removeItem(item)
                    val orderQuantity = item.product.quantityOrder

                    Log.d(TAG, "bindViewHolder: orderQuantity  $orderQuantity")
                    itemView.txt_quantity.text = orderQuantity.toString()
//                    if (orderQuantity == 0){
//                        Toast.makeText(
//                            activity,
//                            "${item.product.nama_menu} telah dihapus dari Keranjang",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        itemView.txt_quantity.visibility = View.GONE
//                        itemView.btn_decrement.visibility = View.GONE
//
//                    }
                    Log.d(TAG, "bindViewHolder: ${item.product}")

                    it.onNext(CartUtils.getCart())

                }
            }).subscribe { cart ->
                var quantity = 0

                cart.forEach { cartItem ->
                    quantity += cartItem.quantity
                }

                val totalPriceIDR = CartUtils.getTotalPriceIDR()

                Log.d(TAG, "bindViewHolder: Item = $cart")
                Log.d(TAG, "bindViewHolder: Total Harga = $totalPriceIDR")

                val cartQuantity = CartUtils.getShoppingCartSize()

                (itemView.context as ShoppingCartActivity).binding.totalPrice.text = totalPriceIDR

            }

        }
    }

}