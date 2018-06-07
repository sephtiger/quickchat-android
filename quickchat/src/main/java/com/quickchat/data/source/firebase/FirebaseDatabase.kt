package com.quickchat.data.source.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.quickchat.data.model.Channel
import com.quickchat.data.model.Message
import com.quickchat.data.model.User

/**
 * Created by seph on 24/05/2018.
 */
internal class FirebaseDatabase {

    companion object Tables {
        const val USERS = "users"
        const val MESSAGES = "messages"
        const val CHANNELS = "channels"
        const val CHANNELMEMBERS = "channel_members"
    }

    private var firebaseFirestore = FirebaseFirestore.getInstance()

    fun createChannel(channel: Channel) =
        firebaseFirestore.collection("$CHANNELS").add(channel)

    fun addChannelUser(id: String, user: User) =
        firebaseFirestore.document("$CHANNELS/$id/$USERS/${user.id}").set(user)

    fun getChannels(id: String) = firebaseFirestore.collection("$USERS/$id/$CHANNELS")

    fun addMessage(id: String, message: Message) =
        firebaseFirestore.collection("$CHANNELS/$id/$MESSAGES").add(message)

    fun getMessages(id: String) = firebaseFirestore.collection("$CHANNELS/$id/$MESSAGES")

    fun setToken(userId: String, token: String) = firebaseFirestore.document("$USERS/$userId")
        .update("token", token)

    fun setChannelOpen(userId: String, channelId: String, isOpen: Boolean) = firebaseFirestore
        .document("$CHANNELS/$channelId/$USERS/$userId")
        .update("is_channel_open", isOpen)
}