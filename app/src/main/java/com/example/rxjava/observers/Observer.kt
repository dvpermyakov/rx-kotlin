package com.example.rxjava.observers

abstract class Observer<T> {

    abstract fun onNext(item: T)

    abstract fun onComplete()

    abstract fun onError(t: Throwable)

}