package com.dvpermyakov.rx.subjects

import com.dvpermyakov.rx.observers.Observer
import com.dvpermyakov.rx.observers.Observer.State

class ReplaySubject<T> : Subject<T>() {
    private val observers: MutableList<Observer<T>> = mutableListOf()
    private var items: MutableList<T> = mutableListOf()
    private var state: State = State.Idle

    override fun subscribeActual(observer: Observer<T>) {
        super.subscribeActual(observer)
        when (state) {
            State.Idle, State.Subscribed -> {
                state = State.Subscribed
                observers.add(observer)
                items.forEach { item ->
                    observer.onNext(item)
                }
            }
            is State.Error -> {
                items.forEach { item ->
                    observer.onNext(item)
                }
                observer.onError((state as State.Error).t)
            }
            State.Completed -> {
                items.forEach { item ->
                    observer.onNext(item)
                }
                observer.onComplete()
            }
        }
    }

    override fun onNext(item: T) {
        observers.forEach { observer ->
            observer.onNext(item)
        }
    }

    override fun onComplete() {
        state = State.Completed
        observers.forEach { observer ->
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