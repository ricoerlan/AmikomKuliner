package com.example.nearby_resto.data.model

data class DataMenu(val id_menu: String, val nama_menu: String, val harga_menu: Int, val foto_menu: String,
                    var quantityOrder: Int = 0) {
}