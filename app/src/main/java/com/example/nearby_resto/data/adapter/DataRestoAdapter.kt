package com.example.nearby_resto.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nearby_resto.R
import com.example.nearby_resto.data.model.DataResto
import com.example.nearby_resto.ui.home.HomeActivity
import kotlinx.android.synthetic.main.list_resto.view.*

class DataRestoAdapter (private val listResto: List<DataResto>): RecyclerView.Adapter<DataRestoAdapter.Holder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_resto,parent,false))
    }

    override fun getItemCount(): Int = listResto.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.et1.text = listResto?.get(position).nama
        holder.view.et2.text = listResto?.get(position).lokasi

        Glide.with(holder.itemView.context)
            .load("http://11.12.13.20/Nearby_Resto/images/person.png")
            .placeholder(R.drawable.ic_launcher_background)
            .dontAnimate()
            .into(holder.view.img_view)

        //listResto?.get(position).photo

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}

