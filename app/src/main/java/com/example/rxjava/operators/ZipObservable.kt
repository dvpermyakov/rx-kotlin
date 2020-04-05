package com.example.rxjava.operators

import com.example.rxjava.functions.Zipper
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer

class ZipObservable<T, R>(
    private val observables: List<Observable<T>>,
    private val zipper: Zipper<T, R>
) : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        ZipCoordinator(observer, zipper).subscribe(observables)
    }

    class ZipCoordinator<T, R>(
        private val observer: Observer<R>,
        private val zipper: Zipper<T, R>
    ) {
        private var items: MutableList<T?> = mutableListOf()
        private var isCancelled = false
        private var completableSet = mutableSetOf<Int>()

        fun subscribe(observables: List<Observable<T>>) {
            repeat(observables.count()) {
                items.add(null)
            }
            observables.forEachIndexed { index, observable ->
                observable.subscribe(
                    ZipObserver(
                        index = index,
                        coordinator = this
                    )
                )
            }
        }

        fun onNextForIndex(index: Int, item: T) {
            if (!isCancelled) {
                items[index] = item

                getValidItems()?.let { validItems ->
                    observer.onNext(zipper.apply(validItems))
                    (0 until items.count()).forEach { index ->
                        items[index] = null
                    }
                }
            }
        }

        fun sendError(t: Throwable) {
            isCancelled = true
            observer.onError(t)
        }

        fun sendCompletable(index: Int) {
            completableSet.add(index)
            if (completableSet.containsAll((0 until items.count()).toList())) {
                observer.onComplete()
            }
        }

        private fun getValidItems(): List<T>? {
            val validItems = mutableListOf<T>()
            items.forEach { item ->
                if (item == null) {
                    return null
                } else {
                    validItems.add(item)
                }
            }
            return validItems
        }
    }

    class ZipObserver<T, R>(
        private val index: Int,
        private val coordinator: ZipCoordinator<T, R>
    ) : Observer<T>() {

        override fun onNext(item: T) {
            coordinator.onNextForIndex(index, item)
        }

        override fun onComplete() {
            coordinator.sendCompletable(index)
        }

        override fun onError(t: Throwable) {
            coordinator.sendError(t)
        }
    }
}