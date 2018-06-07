package com.quickchat.data.interactor

import com.quickchat.data.ChannelRepository
import com.quickchat.data.model.Channel
import io.reactivex.Flowable

/**
 * Created by seph on 24/05/2018.
 */
internal class GetChannels(
        private val repository: ChannelRepository,
        private val id: String) : UseCase<List<Channel>>() {

    override
    fun useCaseObservable() : Flowable<List<Channel>> = repository.getChannels(id)
}