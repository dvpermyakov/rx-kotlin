package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.functions.Function
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class DoOnSubscribeObservable<T>(
    private val observable: Observable<T>,
    private val function: Function
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        function.action()
        observable.subscribe(observer)
    }
}