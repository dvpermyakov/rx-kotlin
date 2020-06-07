package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.functions.MapFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observers.Observer

class CombineLatestObservable<T, R>(
    private val observables: List<Observable<T>>,
    private val combiner: MapFunction<List<T>, R>
) : Observable<R>() {

    override fun subscribeActual(observer: Observer<R>) {
        CombineLatestCoordinator(observer, combiner).subscribe(observables)
    }

    class CombineLatestCoordinator<T, R>(
        private val observer: Observer<R>,
        private val combiner: MapFunction<List<T>, R>
    ) {
        private var shouldItemsSize = 0
        private val items: LinkedHashMap<Int, T> = linkedMapOf()
        private var isDone = false

        fun subscribe(observables: List<Observable<T>>) {
            shouldItemsSize = observables.size
            observables.forEachIndexed { index, observable ->
                observable.subscribe(
                    CombineLatestObserver(
                        index = index,
                        coordinator = this
                    )
                )
            }
        }

        fun onNextForIndex(item: T, index: Int) {
            if (!isDone) {
                items[index] = item
                if (items.size == shouldItemsSize) {
                    val valueList = (0 until shouldItemsSize).map { items.getValue(it) }
                    val value = combiner.map(valueList)
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

    class CombineLatestObserver<T, R>(
        private val index: Int,
        private val coordinator: CombineLatestCoordinator<T, R>
    ) : Observer<T> {

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