package com.example.nearby_resto.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.ui.main.detail.DetailRestoActivity
import com.example.nearby_resto.R
import com.example.nearby_resto.data.ApiServices.ApiResto.BASE_URL
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.data.model.KategoriResto
import com.example.nearby_resto.ui.main.detail.DetailKategoriActivity
import kotlinx.android.synthetic.main.list_categories.view.*
import kotlinx.android.synthetic.main.list_resto.view.*

class DataKategoriAdapter (private val listKategori: List<KategoriResto>): RecyclerView.Adapter<DataKategoriAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_categories,parent,false))
    }

    override fun getItemCount(): Int = listKategori.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.tv_nama_kategori.text = listKategori[position].nama_kategori

        val URL_FOTO = BASE_URL + "TableKategori/" + listKategori[position].image_kategori

        Glide.with(holder.itemView.context)
            .load(URL_FOTO)
            .placeholder(R.drawable.gmaps_64px)
            .dontAnimate()
            .override(50,50)
            .into(holder.view.image_kategori)

        holder.view.image_kategori.setOnClickListener {

            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, DetailKategoriActivity::class.java)

            intent.putExtra("id_kategori", listKategori[position].id_kategori)
            intent.putExtra("nama_kategori", listKategori[position].nama_kategori)
            intent.putExtra("image_kategori", listKategori[position].image_kategori)

            // method startActivity cma bisa di pake di activity/fragment
            // jadi harus masuk ke context dulu
            activity.startActivity(intent)
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)


}