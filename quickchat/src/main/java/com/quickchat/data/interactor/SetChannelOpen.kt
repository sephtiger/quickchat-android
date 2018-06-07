package com.quickchat.data.interactor

import com.quickchat.data.UserRepository
import io.reactivex.Flowable

/**
 * Created by seph on 30/05/2018.
 */

internal class SetChannelOpen(
        private val repository: UserRepository,
        private val userId: String,
        private val channelId: String,
        private val isOpen: Boolean) : UseCase<Boolean>() {

    override
    fun useCaseObservable(): Flowable<Boolean> = repository.setChannelOpen(userId, channelId, isOpen)
}