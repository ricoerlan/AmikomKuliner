package com.example.nearby_resto.data.model

data class DataTransaksi (
    val id_transaksi: Int?,
    val id_pesanan: Int?,
    val no_meja: Int?,
    val nama_pemesan: String?,
    val email: String?,
    val menu: String?,
    val total_harga: Int?,
    val status_transksi: String?,
    val tanggal_transaksi: String?
)