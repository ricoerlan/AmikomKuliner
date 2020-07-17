package com.example.nearby_resto.data

import com.example.nearby_resto.data.model.DataResto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


class ApiServices {

    object ApiResto {

        const val BASE_URL = "http://11.12.13.20/Nearby_Resto/"

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
            @GET("getAllResto.php")
            suspend fun showList(): List<DataResto>
        }


    }
