package com.quickchat.data.interactor

import com.quickchat.data.UserRepository
import io.reactivex.Flowable

/**
 * Created by seph on 29/05/2018.
 */
internal class SetToken(
        private val repository: UserRepository,
        private val userId: String,
        private val token: String) : UseCase<Boolean>() {

    override
    fun useCaseObservable(): Flowable<Boolean> = repository.setToken(userId, token)
}