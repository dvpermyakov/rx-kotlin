package com.example.rxjava.subjects

import com.example.rxjava.observers.Observer
import com.example.rxjava.observers.Observer.State

class BehaviourSubject<T> : Subject<T>() {
    private val observers: MutableList<Observer<T>> = mutableListOf()
    private var lastItem: T? = null
    private var state: State = State.Idle

    override fun subscribeActual(observer: Observer<T>) {
        super.subscribeActual(observer)
        when (state) {
            State.Idle, State.Subscribed -> {
                state = State.Subscribed
                observers.add(observer)
                lastItem?.let(observer::onNext)
            }
            is State.Error -> {
                observer.onError((state as State.Error).t)
            }
            State.Completed -> {
                observer.onComplete()
            }
        }
    }

    override fun onNext(item: T) {
        lastItem = item
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