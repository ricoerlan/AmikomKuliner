package com.example.nearby_resto.data

import com.example.nearby_resto.data.model.DataMenu
import com.example.nearby_resto.data.model.DataResto
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
            suspend fun showList(): List<DataResto>

            @GET("TableMenu/GetMenuByIdResto.php")
            fun showMenu(
                @Query("id_resto", encoded = true) id_resto: Int?
            ): Call<List<DataMenu>>

            @GET("TableResto/GetRestoByIdResto.php")
            fun showScannedResto(
                @Query("id_resto", encoded = true) id_resto: Int?
            ): Call<List<DataResto>>

            @FormUrlEncoded
            @POST("search.php")
            fun search(@Field("search") search: String?): Call<DataResto?>?
        }

    }
