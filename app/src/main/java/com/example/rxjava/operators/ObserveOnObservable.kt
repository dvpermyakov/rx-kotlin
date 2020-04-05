package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer
import com.example.rxjava.shedulers.Scheduler

class ObserveOnObservable<T>(
    private val observable: Observable<T>,
    private val scheduler: Scheduler
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(ObserveOnObserver(observer, scheduler))
    }

    class ObserveOnObserver<T>(
        private val observer: Observer<T>,
        private val scheduler: Scheduler
    ) : Observer<T>() {

        override fun onNext(item: T) {
            scheduler.schedule(Runnable { observer.onNext(item) })
        }

        override fun onComplete() {
            scheduler.schedule(Runnable { observer.onComplete() })

        }

        override fun onError(t: Throwable) {
            scheduler.schedule(Runnable { observer.onError(t) })
        }
    }
}