package com.dvpermyakov.rx.observables

import com.dvpermyakov.rx.observers.Observer

open class Observable<T> : Disposable {

    open fun subscribeActual(observer: Observer<T>) = Unit

    open fun unsubscribeActual() = Unit

    fun subscribe(observer: Observer<T>): Disposable {
        observer.onSubscribe()
        subscribeActual(observer)
        return this
    }

    final override fun dispose() {
        unsubscribeActual()
    }

    companion object
}