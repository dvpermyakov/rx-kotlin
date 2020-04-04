package com.example.rxjava.operators

import com.example.rxjava.functions.Callable
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class FromCallableObservable<T>(
    private val callable: Callable<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observer.onNext(callable.call())
        observer.onComplete()
    }
}