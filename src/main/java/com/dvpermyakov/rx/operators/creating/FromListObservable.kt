package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

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