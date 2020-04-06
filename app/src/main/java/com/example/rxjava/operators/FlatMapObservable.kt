package com.example.rxjava.operators

import com.example.rxjava.functions.ObservableMapFunction
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer
import com.example.rxjava.observers.Observer.State

class FlatMapObservable<T, R>(
    private val observable: Observable<T>,
    private val mapping: ObservableMapFunction<T, R>
) : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        observable.subscribe(FlatMapObserver(observer, mapping))
    }

    class FlatMapObserver<T, R>(
        private val observer: Observer<R>,
        private val mapping: ObservableMapFunction<T, R>
    ) : Observer<T> {

        private var mainState: State = State.Subscribed
        private val innerObservers = mutableListOf<FlatMapInnerObserver<R>>()

        override fun onNext(item: T) {
            if (mainState is State.Subscribed) {
                val observer = FlatMapInnerObserver(observer)
                innerObservers.add(observer)
                mapping.map(item).subscribe(observer)
            }
        }

        override fun onComplete() {
            mainState = State.Completed
            tryToComplete()
        }

        override fun onError(t: Throwable) {
            mainState = State.Error(t)
            innerObservers.forEach { observer ->
                observer.isCancelled = true
            }
            observer.onError(t)
        }

        private fun tryToComplete() {
            if (innerObservers.all { observer -> observer.isCompleted } && mainState is State.Completed) {
                observer.onComplete()
            }
        }

        inner class FlatMapInnerObserver<R>(
            private val observer: Observer<R>
        ) : Observer<R> {

            var isCancelled = false
            var isCompleted = false

            override fun onNext(item: R) {
                if (!isCancelled) {
                    observer.onNext(item)
                }
            }

            override fun onComplete() {
                isCompleted = true
                this@FlatMapObserver.tryToComplete()
            }

            override fun onError(t: Throwable) {
                this@FlatMapObserver.onError(t)
            }
        }
    }
}