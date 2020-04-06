package com.example.rxjava.observables

import com.example.rxjava.observers.Observer

open class Observable<T> : Disposable {
    private var observer: Observer<T>? = null

    open fun subscribeActual(observer: Observer<T>) = Unit

    // todo: it doesn't work with several subscriptions
    fun subscribe(observer: Observer<T>): Disposable {
        subscribeActual(observer)
        this.observer = observer
        return this
    }

    override fun dispose() {
        observer = null
    }

    companion object
}