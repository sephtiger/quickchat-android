package com.quickchat.data.interactor

import com.quickchat.data.MessageRepository
import com.quickchat.data.model.User
import io.reactivex.Flowable

/**
 * Created by seph on 24/05/2018.
 */
internal class SendMessage(
        private val repository: MessageRepository,
        private val id: String,
        private val content: String,
        private val user: User) : UseCase<Boolean>() {

    override
    fun useCaseObservable() : Flowable<Boolean> = repository.addMessage(id, content, user)
}