package com.example.rxjava.observers

interface Observer<T> {

    fun onNext(item: T)

    fun onComplete()

    fun onError(t: Throwable)

    sealed class State {
        object Subscribed : State()
        object Completed : State()
        object Error : State()
    }
}