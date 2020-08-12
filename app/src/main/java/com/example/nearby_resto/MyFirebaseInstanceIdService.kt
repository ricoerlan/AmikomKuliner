package com.example.nearby_resto

import android.content.Context
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseInstanceIdService : FirebaseMessagingService() {
    val TAG = MyFirebaseInstanceIdService::class.java.simpleName

    override fun onNewToken(p0: String) {
        super.onNewToken(p0!!)
        Log.e(TAG,p0)
//        getSharedPreferences("_", MODE_PRIVATE).edit().putString("token", p0).apply();

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i("SellerFirebaseService ", "Message :: $remoteMessage")

        val data = remoteMessage.data
        val body = data["body"]
        val title  = data["title"]
        //create your own notification to display it on notification tray
    }

//    fun getToken(): String? {
//        return getSharedPreferences("_", Context.MODE_PRIVATE)
//            .getString("token", "empty")
//    }
}