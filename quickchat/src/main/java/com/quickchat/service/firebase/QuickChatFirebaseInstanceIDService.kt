package com.quickchat.service.firebase

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.quickchat.QuickChat
import com.quickchat.data.interactor.Result
import io.reactivex.functions.Consumer

/**
 * Created by seph on 29/05/2018.
 */

class QuickChatFirebaseInstanceIDService: FirebaseInstanceIdService() {

    @android.support.annotation.WorkerThread
    override fun onTokenRefresh() {
        var token = FirebaseInstanceId.getInstance().token
        Log.d("QuickChat", "onTokenRefresh() $token")
        token?.let { updateChatToken(it) }
    }

    private fun updateChatToken(token: String) {
        try {
            QuickChat.getInstance().setToken(token).subscribe(Consumer{
                when(it) {
                    is Result.Success<*> -> Log.d("QuickChat", "onTokenRefresh() Success")
                    is Result.Error -> Log.e("QuickChat", "onTokenRefresh() Error: ${it.message}")
                }
            })
        }
        catch (e: IllegalStateException) {
            // do nothing
        }
    }
}