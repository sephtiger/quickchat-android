package com.quickchat.data.interactor

import com.quickchat.data.ChannelRepository
import com.quickchat.data.model.User
import io.reactivex.Flowable

/**
 * Created by seph on 23/05/2018.
 */

internal class AddChannel(
        private val repository: ChannelRepository,
        private val id : String,
        private val property : User,
        private val user : User) : UseCase<String>() {

    override
    fun useCaseObservable() : Flowable<String> = repository.createChannel(id, property, user)
}