package com.example.nearby_resto.ui.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.nearby_resto.MyFirebaseInstanceIdService
import com.example.nearby_resto.data.ApiServices
import com.example.nearby_resto.data.UserRepository
import com.example.nearby_resto.data.model.Value
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val TAG = AuthViewModel::class.java.simpleName

    //email and password for the input
    var namaUser: String? = null
    var email: String? = null
    var password: String? = null
//    var token: String? = firebasetoken.getToken()

    //auth listener
    var authListener: AuthListener? = null


    val currentUser = FirebaseAuth.getInstance().currentUser


    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    //function to perform login
    fun login() {

        //validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        //authentication started
        authListener?.onStarted()

        //calling login from repository to perform the actual authentication
        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                authListener?.onSuccess()
            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    //Doing same thing with signup
    fun signup() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }
        authListener?.onStarted()
        
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })

        disposables.add(disposable)

        val call:Call<Value>  = ApiServices.ApiResto.retrofitService.registrasiUser(namaUser,email, "token" , user?.uid)
        call.enqueue(object : Callback<Value?> {
            override fun onResponse(
                call: Call<Value?>?,
                response: Response<Value?>
            ) {
                val value: String? = response.body()?.value
                val result: String? = response.body()?.result
                if (value == "1") {
                    Log.d(TAG, "onResponse: $result")
//                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
                } else {
                    Log.d(TAG, "onResponse: $result")
//                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Value?>, t: Throwable) {
                Log.d(TAG, "onFailure: Jaringan error")
//                Toast.makeText(this@MainActivity, "Jaringan Error!", Toast.LENGTH_SHORT).show()
            }
        })

    }



    fun goToSignup(view: View) {
        Intent(view.context, SignUpActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}