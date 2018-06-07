package com.quickchat.data

import io.reactivex.Flowable

/**
 * Created by seph on 29/05/2018.
 */

internal interface UserRepository {

    fun setToken(userId: String, token: String) : Flowable<Boolean>

    fun setChannelOpen(userId: String, channelId: String, isOpen: Boolean) : Flowable<Boolean>
}