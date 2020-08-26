package com.example.nearby_resto.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.data.ApiServices.ApiResto.BASE_URL
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.ui.main.detail.DetailRestoActivity
import kotlinx.android.synthetic.main.list_resto.view.*

class DataRestoSearchAdapter (private val listResto: List<DataResto>): RecyclerView.Adapter<DataRestoSearchAdapter.Holder>(){

    private lateinit var listRestoData: MutableList<DataResto>
    private var arrayListResto: ArrayList<DataResto>? = ArrayList(listResto)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_search_resto,parent,false))
    }

    override fun getItemCount(): Int = listResto.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.et1.text = listResto[position].nama
        holder.view.et2.text = listResto[position].lokasi

        val URL_FOTO = BASE_URL + "TableResto/" + listResto[position].photo

        Glide.with(holder.itemView.context)
            .load(URL_FOTO)
            .placeholder(R.drawable.ic_launcher_background)
            .dontAnimate()
            .into(holder.view.img_view)

        holder.itemView.setOnClickListener {

            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, DetailRestoActivity::class.java)

            // sisipkan data ke intent
            intent.putExtra("id_resto", listResto.get(position).id_resto)
            intent.putExtra("nama_resto", listResto.get(position).nama)
            intent.putExtra("lokasi_resto", listResto.get(position).lokasi)
            intent.putExtra("latitude_resto", listResto[position].latitude)
            intent.putExtra("longitude_resto", listResto[position].longitude)
            intent.putExtra("photo_resto", listResto.get(position).photo)

            // method startActivity cma bisa di pake di activity/fragment
            // jadi harus masuk ke context dulu
            activity?.startActivity(intent)

        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    fun filterList(filterdNames: ArrayList<DataResto>) {
        this.arrayListResto = filterdNames
        notifyDataSetChanged()
    }
}

