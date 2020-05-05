package com.dvpermyakov.rx.operators

import com.dvpermyakov.rx.functions.Callable
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class FromCallableObservable<T>(
    private val callable: Callable<T>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observer.onNext(callable.call())
        observer.onComplete()
    }
}