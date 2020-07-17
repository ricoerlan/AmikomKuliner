package com.example.nearby_resto.data.MySql

import com.example.nearby_resto.data.model.DataUserMySql
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySqlApiServices {
    fun addUser(userData: DataUserMySql, onResult: (DataUserMySql?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<DataUserMySql> {
                override fun onFailure(call: Call<DataUserMySql>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<DataUserMySql>, response: Response<DataUserMySql>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
}