package com.example.nearby_resto.data.MySql

import com.example.nearby_resto.data.model.DataUserMySql
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RestApi {

        @Headers("Content-Type: application/json")
        @POST("createUser.php")
        fun addUser(@Body userData: DataUserMySql): Call<DataUserMySql>


}