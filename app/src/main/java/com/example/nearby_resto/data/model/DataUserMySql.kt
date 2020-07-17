package com.example.nearby_resto.data.model

import com.google.gson.annotations.SerializedName

data class DataUserMySql (
    @SerializedName("nama_lengkap") val namaLengkap: String?,
    @SerializedName("user_email") val email: String?,
    @SerializedName("user_password") val password: String?,
    @SerializedName("user_token") val token: String?,
    @SerializedName("user_uid") val userUid: String?
)