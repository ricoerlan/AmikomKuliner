package com.example.nearby_resto.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataResto(val nama: String, val lokasi: String, val photo: String): Parcelable {
}