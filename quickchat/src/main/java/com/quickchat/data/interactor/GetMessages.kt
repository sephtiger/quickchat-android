package com.quickchat.data.interactor

import com.quickchat.data.MessageRepository
import com.quickchat.data.model.Message
import io.reactivex.Flowable

/**
 * Created by seph on 24/05/2018.
 */
internal class GetMessages(
        private val repository: MessageRepository,
        private val id: String) : UseCase<List<Message>>() {

    override
    fun useCaseObservable() : Flowable<List<Message>> = repository.getMessages(id)
}