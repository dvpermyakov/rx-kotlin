package com.dvpermyakov.rx.subjects

import com.dvpermyakov.rx.observers.Observer
import com.dvpermyakov.rx.observers.Observer.State

class AsyncSubject<T> : Subject<T>() {
    private val observers: MutableList<Observer<T>> = mutableListOf()
    private var lastItem: T? = null
    private var state: State = State.Idle

    override fun subscribeActual(observer: Observer<T>) {
        super.subscribeActual(observer)
        when (state) {
            State.Idle, State.Subscribed -> {
                state = State.Subscribed
                observers.add(observer)
            }
            is State.Error -> {
                observer.onError((state as State.Error).t)
            }
            State.Completed -> {
                lastItem?.let(observer::onNext)
                observer.onComplete()
            }
        }
    }

    override fun onNext(item: T) {
        lastItem = item
    }

    override fun onComplete() {
        state = State.Completed
        observers.forEach { observer ->
            lastItem?.let(observer::onNext)
            observer.onComplete()
        }
    }

    override fun onError(t: Throwable) {
        state = State.Error(t)
        observers.forEach { observer ->
            observer.onError(t)
        }
    }

    override fun dispose() {
        observers.clear()
    }

}