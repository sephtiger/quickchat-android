package com.quickchat.data

import com.quickchat.data.model.Channel
import com.quickchat.data.model.User
import io.reactivex.Flowable

/**
 * Created by seph on 23/05/2018.
 */

internal interface ChannelRepository {

    fun createChannel(name: String,  property: User, user: User) : Flowable<String>

    fun getChannels(id: String) : Flowable<List<Channel>>
}