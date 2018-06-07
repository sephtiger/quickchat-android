package com.quickchat.data

import com.quickchat.data.model.Message
import com.quickchat.data.model.User
import io.reactivex.Flowable

/**
 * Created by seph on 24/05/2018.
 */
internal interface MessageRepository {

    fun addMessage(id: String, content: String, user: User) : Flowable<Boolean>

    fun getMessages(id: String) : Flowable<List<Message>>
}