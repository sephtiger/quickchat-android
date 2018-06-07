package com.quickchat.data.source.firebase

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.quickchat.data.ChannelRepository
import com.quickchat.data.model.Channel
import com.quickchat.data.model.User
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.functions.BiFunction
import java.util.*

/**
 * Created by seph on 23/05/2018.
 */

internal class FirebaseChannelRepository : ChannelRepository {

    val firebaseDatabase = FirebaseDatabase()

    override fun getChannels(id: String): Flowable<List<Channel>> = Flowable.create({
        val list = ArrayList<Channel>()
        firebaseDatabase.getChannels(id)
                .orderBy("updated_at", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshots, _ ->
            run {
                querySnapshots?.documentChanges?.forEach {
                    when (it.type) {
                        DocumentChange.Type.ADDED -> list.add(createItemFromDocument(it.document))
                        DocumentChange.Type.MODIFIED -> {
                            list.replace(createItemFromDocument(it.document))
                            list.sortWith(compareByDescending{it.updatedAt})
                        }
                        DocumentChange.Type.REMOVED -> list.remove(createItemFromDocument(it.document))
                    }
                }
                it.onNext(list)
            }
        }
    }, BackpressureStrategy.BUFFER)
    
    override fun createChannel(name: String, property: User, user: User): Flowable<String> =
        Single.create(SingleOnSubscribe<String> { emitter ->

            var channel = Channel()
            channel.name = name
            channel.updatedAt = Date()

            firebaseDatabase.createChannel(channel)
                .addOnSuccessListener { emitter.onSuccess(it.id) }
                .addOnFailureListener { emitter.onError(it) }

        })
        .flatMap {
            Single.zip(
                addChannelUser(it, property),
                addChannelUser(it, user),
                BiFunction<Boolean, Boolean, String> { _, _ -> it }
            )
        }.toFlowable()


    private fun addChannelUser(id: String, user: User): Single<Boolean> = Single.create({ emitter ->

        firebaseDatabase.addChannelUser(id, user)
            .addOnSuccessListener { emitter.onSuccess(true) }
            .addOnFailureListener { emitter.onError(it) }
    })

    private fun <T> ArrayList<T>.replace(t : T): Boolean {
        return this.remove(t) && this.add(t)
    }

    private fun createItemFromDocument(document: QueryDocumentSnapshot) : Channel {
        return document.toObject(Channel::class.java)
    }
}