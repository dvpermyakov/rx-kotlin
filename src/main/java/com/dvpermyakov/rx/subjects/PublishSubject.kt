package com.dvpermyakov.rx.subjects

import com.dvpermyakov.rx.observers.Observer

class PublishSubject<T> : Subject<T>() {
    private val observers: MutableList<Observer<T>> = mutableListOf()

    override fun subscribeActual(observer: Observer<T>) {
        super.subscribeActual(observer)
        observers.add(observer)
    }

    override fun onNext(item: T) {
        observers.forEach { observer ->
            observer.onNext(item)
        }
    }

    override fun onComplete() {
        observers.forEach { observer ->
            observer.onComplete()
        }
    }

    override fun onError(t: Throwable) {
        observers.forEach { observer ->
            observer.onError(t)
        }
    }
}