package com.demo.quickchat

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * Created by seph on 06/06/2018.
 */
class App : Application() {

    override
    fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
