package com.example.rxjava.observers

import com.example.rxjava.observables.Disposable

abstract class Observer<T> {

    open fun onSubscribe(disposable: Disposable) {
    }

    abstract fun onNext(item: T)

    abstract fun onComplete()

    abstract fun onError(t: Throwable)

}