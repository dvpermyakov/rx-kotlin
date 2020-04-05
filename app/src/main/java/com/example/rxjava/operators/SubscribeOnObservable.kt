package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer
import com.example.rxjava.shedulers.Scheduler

class SubscribeOnObservable<T>(
    private val observable: Observable<T>,
    private val scheduler: Scheduler
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        scheduler.schedule(Runnable {
            observable.subscribe(observer)
        })
    }
}