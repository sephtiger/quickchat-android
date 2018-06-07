package com.quickchat.data.source.firebase

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.quickchat.data.MessageRepository
import com.quickchat.data.model.Message
import com.quickchat.data.model.User
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import java.util.*

/**
 * Created by seph on 24/05/2018.
 */

internal class FirebaseMessageRepository: MessageRepository {

    val firebaseDatabase = FirebaseDatabase()

    override fun addMessage(id: String, content: String, user: User): Flowable<Boolean> = Flowable.create({ emitter ->

        var message = Message()
        message.type = "text"
        message.content = content
        message.sender = user
        message.createdAt = Date()

        firebaseDatabase.addMessage(id, message)
                .addOnSuccessListener {
                    emitter.onNext(true)
                    emitter.onComplete()
                }
                .addOnFailureListener { emitter.onError(it) }
    }, BackpressureStrategy.BUFFER)

    override fun getMessages(id: String): Flowable<List<Message>> = Flowable.create({
        val list = ArrayList<Message>()
        firebaseDatabase.getMessages(id)
                .orderBy("created_at")
                .addSnapshotListener { querySnapshots, _ ->
            run {
                querySnapshots?.documentChanges?.forEach {
                    when (it.type) {
                        DocumentChange.Type.ADDED -> list.add(createItemFromDocument(it.document))
                        DocumentChange.Type.MODIFIED -> list.replace(createItemFromDocument(it.document))
                        DocumentChange.Type.REMOVED -> list.remove(createItemFromDocument(it.document))
                    }
                }
                it.onNext(list)
            }
        }
    }, BackpressureStrategy.BUFFER)

    private fun <T> ArrayList<T>.replace(t : T) : Boolean {
        return this.remove(t) && this.add(t)
    }

    private fun createItemFromDocument(document: QueryDocumentSnapshot) : Message {
        return document.toObject(Message::class.java)
    }
}