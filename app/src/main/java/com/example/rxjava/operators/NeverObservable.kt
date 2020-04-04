package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class NeverObservable<T> : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
    }
}