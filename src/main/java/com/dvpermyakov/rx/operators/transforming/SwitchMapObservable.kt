package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.functions.ObservableMapFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer
import com.dvpermyakov.rx.observers.Observer.State

class SwitchMapObservable<T, R>(
    private val observable: Observable<T>,
    private val mapping: ObservableMapFunction<T, R>
) : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        observable.subscribe(
            SwitchMapObserver(
                observer,
                mapping
            )
        )
    }

    class SwitchMapObserver<T, R>(
        private val observer: Observer<R>,
        private val mapping: ObservableMapFunction<T, R>
    ) : Observer<T> {

        private var mainState: State = State.Subscribed
        private var currentObservable: SwitchMapInnerObserver<R>? = null

        override fun onNext(item: T) {
            if (mainState is State.Subscribed) {
                currentObservable?.isCancelled = true
                val observer = SwitchMapInnerObserver(observer)
                currentObservable = observer
                mapping.map(item).subscribe(observer)
            }
        }

        override fun onComplete() {
            mainState = State.Completed
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }

        private fun tryToComplete() {
            if (mainState is State.Completed) {
                observer.onComplete()
            }
        }

        inner class SwitchMapInnerObserver<R>(
            private val observer: Observer<R>
        ) : Observer<R> {

            var isCancelled = false

            override fun onNext(item: R) {
                if (!isCancelled) {
                    observer.onNext(item)
                }
            }

            override fun onComplete() {
                if (!isCancelled) {
                    this@SwitchMapObserver.tryToComplete()
                }
            }

            override fun onError(t: Throwable) {
                if (!isCancelled) {
                    this@SwitchMapObserver.onError(t)
                }
            }
        }
    }
}