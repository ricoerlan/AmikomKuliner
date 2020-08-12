package com.example.nearby_resto.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_resto.R
import com.example.nearby_resto.data.model.DataTransaksi
import kotlinx.android.synthetic.main.list_transaksi.view.*

class DataTransaksiAdapter (private val listTransaksi: List<DataTransaksi>?): RecyclerView.Adapter<DataTransaksiAdapter.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_transaksi,parent,false))
    }

    override fun getItemCount(): Int = listTransaksi!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.tv_nama_resto_transaksi.text = listTransaksi?.get(position)?.no_meja.toString()
        holder.view.tv_nama_pemesan_transaksi.text = listTransaksi!![position].nama_pemesan
        holder.view.tv_tanggal_transaksi.text = listTransaksi!![position].tanggal_transaksi
        holder.view.tv_status_transaksi.text = "Sukses"
        holder.view.tv_total_harga_transaksi.text = listTransaksi!![position].total_harga.toString()

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}

