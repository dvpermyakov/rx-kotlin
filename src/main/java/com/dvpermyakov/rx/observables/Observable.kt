package com.dvpermyakov.rx.observables

import com.dvpermyakov.rx.functions.Consumer
import com.dvpermyakov.rx.functions.Function
import com.dvpermyakov.rx.observers.DisposableObserver
import com.dvpermyakov.rx.observers.Observer

open class Observable<T> {

    open fun subscribeActual(observer: Observer<T>) = Unit

    fun subscribe(observer: Observer<T>) {
        observer.onSubscribe()
        subscribeActual(observer)
    }

    fun subscribe(
        onNext: Consumer<T>,
        onError: Consumer<Throwable>,
        onComplete: Function
    ): Disposable {
        val observer = DisposableObserver(
            onNext = onNext,
            onError = onError,
            onComplete = onComplete
        )
        observer.onSubscribe()
        subscribeActual(observer)

        return observer
    }

    companion object
}