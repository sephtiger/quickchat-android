package com.quickchat.data.source.firebase

import com.quickchat.data.UserRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Created by seph on 29/05/2018.
 */

internal class FirebaseUsersRepository: UserRepository {

    val firebaseDatabase = FirebaseDatabase()

    override fun setToken(userId: String, token: String): Flowable<Boolean> = Flowable.create({ emitter ->

        firebaseDatabase.setToken(userId, token)
                .addOnSuccessListener {
                    emitter.onNext(true)
                    emitter.onComplete()
                }
                .addOnFailureListener { emitter.onError(it) }
    }, BackpressureStrategy.BUFFER)

    override fun setChannelOpen(userId: String, channelId: String, isOpen: Boolean): Flowable<Boolean> = Flowable.create({ emitter ->

        firebaseDatabase.setChannelOpen(userId, channelId, isOpen)
                .addOnSuccessListener {
                    emitter.onNext(true)
                    emitter.onComplete()
                }
                .addOnFailureListener { emitter.onError(it) }
    }, BackpressureStrategy.BUFFER)
}