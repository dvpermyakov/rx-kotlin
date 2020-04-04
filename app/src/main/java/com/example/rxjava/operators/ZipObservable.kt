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

        fun subscribe(observables: List<Observable<T>>) {
            repeat(observables.count()) {
                items.add(null)
            }
            observables.forEachIndexed { index, observable ->
                observable.subscribe(
                    ZipObserver(
                        observer = observer,
                        index = index,
                        coordinator = this
                    )
                )
            }
        }

        fun setItem(index: Int, item: T): R? {
            items[index] = item

            var result: R? = null
            getValidItems()?.let { validItems ->
                result = zipper.apply(validItems)
                (0 until items.count()).forEach { index ->
                    items[index] = null
                }
            }
            return result
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
        private val observer: Observer<R>,
        private val index: Int,
        private val coordinator: ZipCoordinator<T, R>
    ) : Observer<T>() {

        override fun onNext(item: T) {
            coordinator.setItem(index, item)?.let { result ->
                observer.onNext(result)
            }
        }

        override fun onComplete() {
            observer.onComplete()
        }

        override fun onError(t: Throwable) {
            observer.onError(t)
        }
    }
}