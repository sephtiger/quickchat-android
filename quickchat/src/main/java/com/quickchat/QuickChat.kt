package com.quickchat

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.quickchat.data.interactor.*
import com.quickchat.data.model.User
import com.quickchat.data.source.firebase.FirebaseChannelRepository
import com.quickchat.data.source.firebase.FirebaseMessageRepository
import com.quickchat.data.source.firebase.FirebaseUsersRepository
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by seph on 23/05/2018.
 */

class QuickChat private constructor(private val user: User) {

    companion object {

        private var user: User? = null

        fun initialize(user: User) {
            this.user = user
            Log.d("QuickChat", "QuickChat.initialize() ${user.name}")
            FirebaseMessaging.getInstance().isAutoInitEnabled = true
            var token = FirebaseInstanceId.getInstance().token
            token?.let { getInstance().setToken(it).subscribe() }
        }

        @Synchronized
        @JvmStatic
        fun getInstance() : QuickChat {
            return  QuickChat(user ?: throw IllegalStateException("QuickChat not properly initialized"))
        }

    }

    private val channelRepository = FirebaseChannelRepository()

    private val messageRepository = FirebaseMessageRepository()

    private val userRepository = FirebaseUsersRepository()

    fun createChannel(id: String, property: User) =
        AddChannel(channelRepository, id, property, user).executeSingle()

    fun getChannels() : Flowable<Result> =
        GetChannels(channelRepository, user.id).execute()

    fun sendMessage(id: String, content: String) =
        SendMessage(messageRepository, id, content, user).executeSingle()

    fun getMessages(id: String) : Flowable<Result> =
        GetMessages(messageRepository, id).execute()

    fun setToken(token: String): Single<Result> =
        SetToken(userRepository, user.id, token).executeSingle()

    /**
     * Sets the active channel of the user. also reset unread count if [isOpen] is true
     *
     * @param channelId ID of the channel
     * @param isOpen value to set either true or false
     */
    fun setChannelOpen(channelId: String, isOpen: Boolean): Single<Result> =
        SetChannelOpen(userRepository, user.id, channelId, isOpen).executeSingle()
}

