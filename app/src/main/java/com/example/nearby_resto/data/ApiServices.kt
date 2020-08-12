package com.example.nearby_resto.data

import com.example.nearby_resto.data.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


class ApiServices {

    object ApiResto {

        const val BASE_URL = "https://hipmagazine.000webhostapp.com/"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

        val retrofitService = retrofit.create(ApiService::class.java)
    }

        // API Data
        interface ApiService {
            @GET("TableResto/getAllResto.php")
            suspend fun getAllResto(): List<DataResto>

            @GET("TableKategori/getAllKategori.php")
            suspend fun getAllCategories(): List<KategoriResto>

            @GET("TableResto/searchResto.php")
            suspend fun search(@Query("search") search: String?): List<DataResto>?

            @GET("TableResto/GetRestoByIdKategori.php")
            suspend fun GetRestoByIdKategori(
                @Query("id_kategori", encoded = true) id_kategori: Int?
            ): List<DataResto>

            @GET("TableMenu/GetMenuByIdResto.php")
            fun showMenu(
                @Query("id_resto", encoded = true) id_resto: Int?
            ): Call<List<DataMenu>>

            @GET("TableResto/GetRestoByIdResto.php")
            fun showScannedResto(
                @Query("id_resto", encoded = true) id_resto: Int?
            ): Call<List<DataResto>>

            @FormUrlEncoded
            @POST("TableUser/createUser.php")
            fun registrasiUser(
                @Field("nama") nama: String?,
                @Field("email") email: String?,
                @Field("token") token: String?,
                @Field("userUid") userUid: String?
            ): Call<Value>

            @FormUrlEncoded
            @POST("TableTransaksi/addTransaction.php")
            fun checkout(
                @Field("id_pesanan") id_pesanan: Int?,
                @Field("no_meja") no_meja: Int?,
                @Field("nama_pemesan") nama_pemesan: String?,
                @Field("email") email: String?,
                @Field("menu") menu: String?,
                @Field("total_harga") total_harga: Int?
            ): Call<Value>

            @GET("TableTransaksi/GetTransactionByEmail.php")
            suspend fun getTransactionList(
                @Query("email") email: String?
            ): List<DataTransaksi>

            @GET("TableTransaksi/CheckTransaction.php")
           suspend fun checkTransaction(
                @Query("id_pesanan") id_pesanan: Int?
            ): List<DataTransaksi>

        }

    }
