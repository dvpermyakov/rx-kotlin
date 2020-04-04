package com.example.rxjava.operators

import com.example.rxjava.functions.Function
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class DoOnSubscribeObservable<T>(
    private val observable: Observable<T>,
    private val function: Function
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        function.action()
        observable.subscribe(observer)
    }
}