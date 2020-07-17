package com.example.nearby_resto

import android.util.Log
import com.example.nearby_resto.ui.auth.AuthViewModel
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.nearby_resto.ui.home.HomeViewModel


class MyFirebaseInstanceIdService : FirebaseMessagingService() {
    val TAG = MyFirebaseInstanceIdService::class.java.simpleName

    override fun onNewToken(p0: String) {
        super.onNewToken(p0!!)
        Log.e(TAG,p0)

        //Method berikut ini digunakan untuk memperoleh token dan mennyimpannya ke server aplikasi
        sendRegistrationToServer(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.i("SellerFirebaseService ", "Message :: $remoteMessage")

        val data = remoteMessage.data
        val body = data["body"]
        val title  = data["title"]
        //create your own notification to display it on notification tray
    }


    private fun sendRegistrationToServer(p0: String?) {
        //Disini kita biarkan kosong saja

    }
}