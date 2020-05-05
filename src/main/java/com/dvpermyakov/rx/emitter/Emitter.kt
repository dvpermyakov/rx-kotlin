package com.dvpermyakov.rx.emitter

interface Emitter<T> {
    fun onNext(item: T)
    fun onComplete()
    fun onError(t: Throwable)
}