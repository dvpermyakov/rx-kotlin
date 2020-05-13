package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.functions.ObservableMapFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer
import com.dvpermyakov.rx.observers.Observer.State

class ConcatMapObservable<T, R>(
    private val observable: Observable<T>,
    private val mapping: ObservableMapFunction<T, R>
) : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        observable.subscribe(
            ConcatMapObserver(
                observer,
                mapping
            )
        )
    }

    class ConcatMapObserver<T, R>(
        private val observer: Observer<R>,
        private val mapping: ObservableMapFunction<T, R>
    ) : Observer<T> {

        private var currentIndex = 0

        private var mainState: State = State.Subscribed
        private val innerObservers = mutableListOf<ConcatMapInnerObserver>()

        override fun onNext(item: T) {
            if (mainState is State.Subscribed) {
                val observer = ConcatMapInnerObserver(innerObservers.size)
                innerObservers.add(observer)
                mapping.map(item).subscribe(observer)
            }
        }

        override fun onComplete() {
            mainState = State.Completed
            tryToComplete(-1)
        }

        override fun onError(t: Throwable) {
            mainState = State.Error(t)
            innerObservers.forEach { observer ->
                observer.isCancelled = true
            }
            observer.onError(t)
        }

        fun onNextForIndex(item: R, index: Int) {
            if (mainState !is State.Error) {
                if (index == currentIndex) {
                    observer.onNext(item)
                }
            }
        }

        private fun tryToComplete(index: Int) {
            if (mainState !is State.Error) {
                if (currentIndex == innerObservers.lastIndex && innerObservers.all { observer -> observer.isCompleted } && mainState is State.Completed) {
                    observer.onComplete()
                } else if (index == currentIndex) {
                    currentIndex++
                    innerObservers[currentIndex].items.forEach { item ->
                        observer.onNext(item)
                    }
                    if (innerObservers[currentIndex].isCompleted) {
                        tryToComplete(currentIndex)
                    }
                }
            }
        }

        inner class ConcatMapInnerObserver(
            private val index: Int
        ) : Observer<R> {

            var isCancelled = false
            var isCompleted = false
            val items = mutableListOf<R>()

            override fun onNext(item: R) {
                if (!isCancelled) {
                    items.add(item)
                    this@ConcatMapObserver.onNextForIndex(item, index)
                }
            }

            override fun onComplete() {
                isCompleted = true
                this@ConcatMapObserver.tryToComplete(index)
            }

            override fun onError(t: Throwable) {
                this@ConcatMapObserver.onError(t)
            }
        }
    }
}