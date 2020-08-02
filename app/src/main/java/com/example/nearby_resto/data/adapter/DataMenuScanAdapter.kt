package com.example.nearby_resto.data.adapter


import android.annotation.SuppressLint
import android.app.Activity
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
import io.paperdb.Paper
import kotlinx.android.synthetic.main.list_menu.view.*

class DataMenuScanAdapter (private val listMenu: List<DataMenu>): RecyclerView.Adapter<DataMenuScanAdapter.Holder>() {

    private val IMAGE_URL: String = "https://hipmagazine.000webhostapp.com/TableMenu/images/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_menu, parent, false)
        )
    }

    override fun getItemCount(): Int = listMenu.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.tv_nama_menu.text = listMenu[position].nama_menu
        holder.view.tv_harga_menu.text = "Rp." + listMenu[position].harga_menu.toString()

        Glide.with(holder.itemView.context)
            .load(IMAGE_URL + listMenu[position].foto_menu)
            .placeholder(R.drawable.ic_launcher_background)
            .override(300, 750)
            .into(holder.view.img_menu)

        holder.bindProduct(listMenu[position])

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindProduct(product: DataMenu) {

                view.addToCart.setOnClickListener { view ->
                    val item = CartItem(product)
                    val activity = itemView.context as Activity
                    Paper.init(activity)

                    CartUtils.addItem(item)
                    //notify users
                    Toast.makeText(
                        activity,
                        "${item.product.nama_menu} telah ditambahkan",
                        Toast.LENGTH_LONG
                    ).show()

                }

                view.removeItem.setOnClickListener { view ->
                    val item = CartItem(product)
                    val activity = itemView.context as Activity
                    Paper.init(activity)

                    CartUtils.removeItem(item, view.context)
                    //notify users
                    Toast.makeText(
                        activity,
                        "${item.product.nama_menu} telah dihapus",
                        Toast.LENGTH_LONG
                    ).show()

                }
        }
    }
}