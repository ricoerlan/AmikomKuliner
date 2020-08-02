package com.example.nearby_resto.data

import com.example.nearby_resto.data.model.DataMenu
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class MyResponse {
    @SerializedName("details")
    @Expose
    private var details: List<DataMenu?>? = null

    @SerializedName("success")
    @Expose
    private var success: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null
    fun getSuccess(): Int? {
        return success
    }

    fun setSuccess(success: Int?) {
        this.success = success
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getDataMenus(): List<DataMenu?>? {
        return details
    }

    fun setDataMenus(details: List<DataMenu?>?) {
        this.details = details
    }
}