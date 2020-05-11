package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class MergeObservableTest {

    @Test
    fun mergeList() {
        val values1 = listOf(1, 2, 3)
        val values2 = listOf(4, 5, 6)
        val values = values1 + values2

        val observer = TestObserver<Int>()
        Observable.mergeList(
            listOf(
                Observable.fromList(values1),
                Observable.fromList(values2)
            )
        ).subscribe(observer)

        observer
            .assertCount(values.size)
            .assertAtIndex(0, values[0])
            .assertAtIndex(1, values[1])
            .assertAtIndex(2, values[2])
            .assertAtIndex(3, values[3])
            .assertAtIndex(4, values[4])
            .assertAtIndex(5, values[5])
            .assertCompletion()
    }

    @Test(timeout = 10000L)
    fun mergeListAsync() {
        val intObservable1 = Observable.create<Int> { emitter ->
            emitter.onNext(1)  // 0L
            Thread.sleep(200L)
            emitter.onNext(2)  // 200L
            Thread.sleep(400L)
            emitter.onNext(3)  // 600L
            emitter.onComplete()
        }.subscribeOn(ThreadScheduler())
        val intObservable2 = Observable.create<Int> { emitter ->
            Thread.sleep(100L)
            emitter.onNext(100)  // 100L
            Thread.sleep(200L)
            emitter.onNext(200)  // 300L
            Thread.sleep(100L)
            emitter.onNext(300)  // 400L
            emitter.onComplete()
        }.subscribeOn(ThreadScheduler())

        val observer = TestObserver<Int>()
        Observable.mergeList(
            listOf(
                intObservable1,
                intObservable2
            )
        ).subscribeOn(ThreadScheduler()).subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(6)
            .assertAtIndex(0, 1)
            .assertAtIndex(1, 100)
            .assertAtIndex(2, 2)
            .assertAtIndex(3, 200)
            .assertAtIndex(4, 300)
            .assertAtIndex(5, 3)
            .assertCompletion()
    }

}