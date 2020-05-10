package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer
import com.dvpermyakov.rx.shedulers.Scheduler

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