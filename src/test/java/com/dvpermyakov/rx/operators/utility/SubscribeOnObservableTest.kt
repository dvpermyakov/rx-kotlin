package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.combining.mergeList
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.filtering.filter
import com.dvpermyakov.rx.operators.transforming.map
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Assert
import org.junit.Test

class SubscribeOnObservableTest {

    @Test
    fun subscribeOnOneOperator() {
        val threadScheduler = ThreadScheduler()

        val observable = Observable.create<Int> { emitter ->
            Assert.assertEquals(
                threadScheduler.lastExecutedThread?.name,
                Thread.currentThread().name
            )
            emitter.onComplete()
        }.subscribeOn(threadScheduler)

        val testObserver = TestObserver<Int>()
        observable.subscribe(testObserver)
        testObserver.waitForFinished()
    }

    @Test
    fun subscribeFirstOnly() {
        val threadSchedulerFirst = ThreadScheduler()
        val threadSchedulerSecond = ThreadScheduler()

        val observable = Observable.create<Int> { emitter ->
            Assert.assertEquals(
                threadSchedulerFirst.lastExecutedThread?.name,
                Thread.currentThread().name
            )
            emitter.onComplete()
        }.subscribeOn(threadSchedulerFirst).subscribeOn(threadSchedulerSecond)

        val testObserver = TestObserver<Int>()
        observable.subscribe(testObserver)
        testObserver.waitForFinished()
    }

    @Test
    fun subscribeOnMultipleOperators() {
        val threadScheduler = ThreadScheduler()

        val observable = Observable.create<Int> { emitter ->
            Assert.assertEquals(
                threadScheduler.lastExecutedThread?.name,
                Thread.currentThread().name
            )
            emitter.onNext(1)
            emitter.onComplete()
        }

        val testObserver = TestObserver<Int>()
        observable
            .filter { it > 0 }
            .map { it * 2 }
            .subscribeOn(threadScheduler)
            .subscribe(testObserver)
        testObserver.waitForFinished()
    }

    @Test
    fun subscribeOnMultipleSchedulers() {
        val threadScheduler1 = ThreadScheduler()
        val observable1 = Observable.create<Int> { emitter ->
            Assert.assertEquals(
                threadScheduler1.lastExecutedThread?.name,
                Thread.currentThread().name
            )
            emitter.onNext(1)
            emitter.onComplete()
        }.subscribeOn(threadScheduler1)

        val threadScheduler2 = ThreadScheduler()
        val observable2 = Observable.create<Int> { emitter ->
            Assert.assertEquals(
                threadScheduler2.lastExecutedThread?.name,
                Thread.currentThread().name
            )
            emitter.onNext(2)
            emitter.onComplete()
        }.subscribeOn(threadScheduler2)

        val threadScheduler3 = ThreadScheduler()
        val testObserver = TestObserver<Int>()
        Observable.mergeList(
            listOf(
                observable1,
                observable2
            )
        ).map { item -> item }.subscribeOn(threadScheduler3).subscribe(testObserver)
        testObserver.waitForFinished()
    }

}