package com.example.nearby_resto.data.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.data.model.CartItem
import com.example.nearby_resto.data.model.DataMenu
import com.google.android.material.snackbar.Snackbar
import io.paperdb.Paper
import kotlinx.android.synthetic.main.list_menu.view.*

class DataMenuAdapter (private val listMenu: List<DataMenu>): RecyclerView.Adapter<DataMenuAdapter.Holder>(){

    private val IMAGE_URL: String = "https://hipmagazine.000webhostapp.com/TableMenu/images/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_menu,parent,false))
    }

    override fun getItemCount(): Int = listMenu.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.tv_nama_menu.text = listMenu[position].nama_menu
        holder.view.tv_harga_menu.text = "Rp." + listMenu[position].harga_menu.toString()

        Glide.with(holder.itemView.context)
            .load(IMAGE_URL + listMenu[position].foto_menu)
            .placeholder(R.drawable.ic_launcher_background)
            .override(300,750)
            .into(holder.view.img_menu)

        holder.view.addToCart.setOnClickListener {
            val activity = holder.itemView.context as Activity

            Toast.makeText(activity,"Scan QRCode di Resto ini untuk Menambah Item ke Keranjang",Toast.LENGTH_LONG).show()
        }

        holder.view.removeItem.setOnClickListener {
            val activity = holder.itemView.context as Activity

            Toast.makeText(activity,"Scan QRCode di Resto ini untuk Menghapus Item dari Keranjang",Toast.LENGTH_LONG).show()
        }

        }


    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}