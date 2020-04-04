package com.example.rxjava.emitter

interface Emitter<T> {
    fun onNext(item: T)
    fun onComplete()
    fun onError(t: Throwable)
}