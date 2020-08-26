package com.example.nearby_resto.data.adapter


import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.utils.CartUtils
import com.example.nearby_resto.data.model.CartItem
import com.example.nearby_resto.data.model.DataMenu
import com.example.nearby_resto.ui.main.detail.ScanCodeDetailResto
import io.paperdb.Paper
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.list_menu.view.*
import kotlinx.android.synthetic.main.list_menu_2.view.*

class DataMenuScanAdapter (private val listMenu: List<DataMenu>): RecyclerView.Adapter<DataMenuScanAdapter.Holder>() {

    private val IMAGE_URL: String = "https://hipmagazine.000webhostapp.com/TableMenu/images/"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_menu_2, parent, false)
        )
    }

    override fun getItemCount(): Int = listMenu.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.txt_food_name.text = listMenu[position].nama_menu
        holder.view.txt_price.text = "Rp." + listMenu[position].harga_menu.toString()

        Glide.with(holder.itemView.context)
            .load(IMAGE_URL + listMenu[position].foto_menu)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.view.roundedImageView)

        holder.bindProduct(listMenu[position])

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("CheckResult")
        fun bindProduct(product: DataMenu) {

            val TAG = DataMenuScanAdapter::class.java.simpleName

            Observable.create(ObservableOnSubscribe<MutableList<CartItem>> {

                view.btn_increment.setOnClickListener { view ->
                    val item = CartItem(product)
                    val activity = itemView.context as Activity
                    val itemSize = CartUtils.getShoppingCartSize().plus(1)
                    Paper.init(activity)

                    CartUtils.addItem(item)
                    val orderQuantity = item.product.quantityOrder
                    val quantity = itemSize

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
                    val item = CartItem(product)
                    val activity = itemView.context as Activity
                    val itemSize = CartUtils.getShoppingCartSize().minus(1)
                    Paper.init(activity)

                    CartUtils.removeItem(item)
                    val orderQuantity = item.product.quantityOrder

                    Log.d(TAG, "bindProduct: quantity ${item.quantity}")
                    Log.d(TAG, "bindProduct: orderQuantity $orderQuantity")

                    itemView.txt_quantity.text = orderQuantity.toString()
                    if (orderQuantity == 0){
                        Toast.makeText(
                            activity,
                            "${item.product.nama_menu} telah dihapus dari Keranjang",
                            Toast.LENGTH_SHORT
                        ).show()
                        itemView.txt_quantity.visibility = View.GONE
                        itemView.btn_decrement.visibility = View.GONE
                    }

                    if (item.product.nama_menu.isEmpty()){
                        itemView.btn_decrement.visibility = View.GONE
                    }
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

                if (cartQuantity == 1){
                    (itemView.context as ScanCodeDetailResto).binding.txtItemCount.text = "$cartQuantity item - "
                }else if (cartQuantity > 1){
                    (itemView.context as ScanCodeDetailResto).binding.txtItemCount.text = "$cartQuantity item(s) - "
                }
                (itemView.context as ScanCodeDetailResto).binding.txtItemPrice.text = totalPriceIDR

            }
        }
    }
}