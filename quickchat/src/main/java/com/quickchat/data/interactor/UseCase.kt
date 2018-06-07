package com.quickchat.data.interactor

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by seph on 23/05/2018.
 */

internal abstract class UseCase<T> {

    abstract fun useCaseObservable() : Flowable<T>


    fun execute() : Flowable<Result> = Flowable.create({ emitter ->

        useCaseObservable()
            .subscribe(
                { emitter.onNext(Result.Success(it)) },
                { emitter.onNext(Result.Error(it.message!!)) },
                { emitter.onComplete() }
            )
    }, BackpressureStrategy.BUFFER)

    fun executeSingle() : Single<Result> = Single.create({ emitter ->

        useCaseObservable()
            .subscribe(
                { emitter.onSuccess(Result.Success(it)) },
                { emitter.onSuccess(Result.Error(it.message!!)) }
            )
    })
}