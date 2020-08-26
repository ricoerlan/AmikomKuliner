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
import com.example.nearby_resto.data.model.DataMenu
import kotlinx.android.synthetic.main.list_menu_2.view.*

class DataMenuAdapter (private val listMenu: List<DataMenu>): RecyclerView.Adapter<DataMenuAdapter.Holder>(){

    private val IMAGE_URL: String = "https://hipmagazine.000webhostapp.com/TableMenu/images/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_menu_2,parent,false))
    }

    override fun getItemCount(): Int = listMenu.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.txt_food_name.text = listMenu[position].nama_menu
        holder.view.txt_price.text = "Rp." + listMenu[position].harga_menu.toString()

        Glide.with(holder.itemView.context)
            .load(IMAGE_URL + listMenu[position].foto_menu)
            .placeholder(R.drawable.ic_launcher_background)
            .override(300,750)
            .into(holder.view.roundedImageView)

        holder.view.btn_increment.setOnClickListener {
            val activity = holder.itemView.context as Activity

            Toast.makeText(activity,"Scan QRCode di Resto ini untuk Menambah Item ke Keranjang",Toast.LENGTH_LONG).show()
        }
//
//        holder.view.removeItem.setOnClickListener {
//            val activity = holder.itemView.context as Activity
//
//            Toast.makeText(activity,"Scan QRCode di Resto ini untuk Menghapus Item dari Keranjang",Toast.LENGTH_LONG).show()
//        }

        }


    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}