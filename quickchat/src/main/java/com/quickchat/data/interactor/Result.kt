package com.quickchat.data.interactor

/**
 * Created by seph on 23/05/2018.
 */

sealed class Result {

    class Success<out T>(val data : T) : Result()

    class Error(val message : String) : Result()
}