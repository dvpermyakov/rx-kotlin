package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.just
import com.dvpermyakov.rx.operators.filtering.filter
import com.dvpermyakov.rx.operators.transforming.map
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Assert
import org.junit.Test

class ObserveOnObservableTest {

    @Test
    fun observeOnOneOperator() {
        val subscriptionThreadScheduler = ThreadScheduler()
        val observerThreadScheduler = ThreadScheduler()

        val observable = Observable.just(1).subscribeOn(subscriptionThreadScheduler)

        val testObserver = object : TestObserver<Int>() {
            override fun onNext(item: Int) {
                super.onNext(item)
                Assert.assertEquals(
                    observerThreadScheduler.lastExecutedThread?.name,
                    Thread.currentThread().name
                )
            }
        }

        observable
            .observeOn(observerThreadScheduler)
            .subscribe(testObserver)

        testObserver.waitForFinished()
    }

    @Test
    fun observeMultipleOperators() {
        val subscriptionThreadScheduler = ThreadScheduler()
        val observerThreadScheduler = ThreadScheduler()
        val mapThreadScheduler = ThreadScheduler()
        val filterThreadScheduler = ThreadScheduler()

        val observable = Observable.just(1).subscribeOn(subscriptionThreadScheduler)

        val testObserver = object : TestObserver<Int>() {
            override fun onNext(item: Int) {
                super.onNext(item)
                Assert.assertEquals(
                    observerThreadScheduler.lastExecutedThread?.name,
                    Thread.currentThread().name
                )
            }
        }

        observable
            .observeOn(mapThreadScheduler)
            .map { item ->
                Assert.assertEquals(
                    mapThreadScheduler.lastExecutedThread?.name,
                    Thread.currentThread().name
                )
                item
            }
            .observeOn(filterThreadScheduler)
            .filter {
                Assert.assertEquals(
                    filterThreadScheduler.lastExecutedThread?.name,
                    Thread.currentThread().name
                )
                true
            }
            .observeOn(observerThreadScheduler)
            .subscribe(testObserver)

        testObserver.waitForFinished()
    }

}