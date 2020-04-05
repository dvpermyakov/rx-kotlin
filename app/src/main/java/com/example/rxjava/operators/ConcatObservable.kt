package com.example.rxjava.operators

import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class ConcatObservable<T>(
    private val observables: List<Observable<T>>
) : Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        ConcatCoordinator(observer).subscribe(observables)
    }

    class ConcatCoordinator<T>(
        private val observer: Observer<T>
    ) {
        private val items: MutableList<MutableList<T>> = mutableListOf()
        private val completedIndices: MutableSet<Int> = mutableSetOf()
        private var isDone = false
        private var currentIndex = 0

        fun subscribe(observables: List<Observable<T>>) {
            repeat(observables.count()) {
                items.add(mutableListOf())
            }
            observables.forEachIndexed { index, observable ->
                observable.subscribe(
                    ConcatObserver(
                        index = index,
                        coordinator = this
                    )
                )
            }
        }

        fun onNextForIndex(item: T, index: Int) {
            if (!isDone) {
                if (index == currentIndex) {
                    observer.onNext(item)
                } else {
                    items[index].add(item)
                }
            }
        }

        fun onError(t: Throwable) {
            if (!isDone) {
                isDone = true
                observer.onError(t)
            }
        }

        fun onComplete(index: Int) {
            if (!isDone) {
                completedIndices.add(index)
                if (completedIndices.size == items.size) {
                    isDone = true
                    observer.onComplete()
                } else {
                    currentIndex++
                    items[currentIndex].forEach { item ->
                        observer.onNext(item)
                    }
                }
            }
        }
    }

    class ConcatObserver<T>(
        private val index: Int,
        private val coordinator: ConcatCoordinator<T>
    ) : Observer<T>() {

        override fun onNext(item: T) {
            coordinator.onNextForIndex(item, index)
        }

        override fun onComplete() {
            coordinator.onComplete(index)
        }

        override fun onError(t: Throwable) {
            coordinator.onError(t)
        }
    }
}