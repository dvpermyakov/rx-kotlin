package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer
import com.dvpermyakov.rx.shedulers.Scheduler

class ObserveOnObservable<T>(
    private val observable: Observable<T>,
    private val scheduler: Scheduler
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        observable.subscribe(
            ObserveOnObserver(
                observer,
                scheduler
            )
        )
    }

    class ObserveOnObserver<T>(
        private val observer: Observer<T>,
        private val scheduler: Scheduler
    ) : Observer<T> {

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