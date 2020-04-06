package com.example.rxjava.subjects

import com.example.rxjava.observers.Observer

class AsyncSubject<T> : Subject<T>() {
    private val observers: MutableList<Observer<T>> = mutableListOf()
    private var lastItem: T? = null

    override fun subscribeActual(observer: Observer<T>) {
        super.subscribeActual(observer)
        observers.add(observer)
    }

    override fun onNext(item: T) {
        lastItem = item
    }

    override fun onComplete() {
        observers.forEach { observer ->
            lastItem?.let(observer::onNext)
            observer.onComplete()
        }
    }

    override fun onError(t: Throwable) {
        observers.forEach { observer ->
            observer.onError(t)
        }
    }

    override fun dispose() {
        observers.clear()
    }

}