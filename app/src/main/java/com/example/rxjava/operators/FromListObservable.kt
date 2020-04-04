package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class FromListObservable<T>(
    private val items: List<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        items.forEach { item ->
            observer.onNext(item)
        }
        observer.onComplete()
    }
}