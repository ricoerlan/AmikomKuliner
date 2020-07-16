package com.example.nearby_resto.data

import com.example.nearby_resto.data.model.DataResto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


class ApiServices {



    companion object {
        const val BASE_URL = "11.12.13.20/Nearby_Resto"

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Companion.BASE_URL)
            .build()



        // API Data
        interface ApiService {
            @GET("getAllResto.php")
            suspend fun showList(): List<DataResto>
        }

        object ApiResto {
            val retrofitService = retrofit.create(ApiService::class.java)
        }

    }

}