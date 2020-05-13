package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class ConcatObservableTest {

    @Test(timeout = 10000L)
    fun concatListAsync() {
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
        Observable.concatList(
            listOf(
                intObservable1,
                intObservable2
            )
        ).subscribeOn(ThreadScheduler()).subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(6)
            .assertAtIndex(0, 1)
            .assertAtIndex(1, 2)
            .assertAtIndex(2, 3)
            .assertAtIndex(3, 100)
            .assertAtIndex(4, 200)
            .assertAtIndex(5, 300)
            .assertCompletion()
    }

}