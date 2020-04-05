package com.example.rxjava.operators

import com.example.rxjava.functions.Zipper
import com.example.rxjava.observables.Observable
import com.example.rxjava.observers.Observer
import java.util.*

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
        private val items: MutableList<Queue<T>> = mutableListOf()
        private var isDone = false

        fun subscribe(observables: List<Observable<T>>) {
            repeat(observables.count()) {
                items.add(LinkedList())
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

        fun onNextForIndex(item: T, index: Int) {
            if (!isDone) {
                items[index].offer(item)

                if (items.all { queue -> queue.isNotEmpty() }) {
                    val value = zipper.apply(items.map { queue -> queue.poll() })
                    observer.onNext(value)
                }
            }
        }

        fun onError(t: Throwable) {
            if (!isDone) {
                isDone = true
                observer.onError(t)
            }
        }

        fun onComplete() {
            if (!isDone) {
                isDone = true
                observer.onComplete()
            }
        }
    }

    class ZipObserver<T, R>(
        private val index: Int,
        private val coordinator: ZipCoordinator<T, R>
    ) : Observer<T>() {

        override fun onNext(item: T) {
            coordinator.onNextForIndex(item, index)
        }

        override fun onComplete() {
            coordinator.onComplete()
        }

        override fun onError(t: Throwable) {
            coordinator.onError(t)
        }
    }
}