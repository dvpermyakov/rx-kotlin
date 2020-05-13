package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.creating.fromCallable
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Ignore
import org.junit.Test

class SubscribeTest {

    @Test
    fun subscribeOneObserver() {
        val observable = Observable.fromCallable { 10 }

        val observer = TestObserver<Int>()
        observable.subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(1)
            .assertAtIndex(0, 10)
            .assertCompletion()
    }

    @Test
    fun subscribeMultipleObservers() {
        val observable = Observable.fromCallable { 10 }

        val observer1 = TestObserver<Int>()
        observable.subscribe(observer1)
        val observer2 = TestObserver<Int>()
        observable.subscribe(observer2)

        observer1
            .waitForFinished()
            .assertCount(1)
            .assertAtIndex(0, 10)
            .assertCompletion()

        observer2
            .waitForFinished()
            .assertCount(1)
            .assertAtIndex(0, 10)
            .assertCompletion()
    }

    @Ignore
    @Test(timeout = 10_000)
    fun disposable() {
        val observable = Observable.create<Int> { emitter ->
            emitter.onNext(1)
            Thread.sleep(100)
            emitter.onNext(2)
            Thread.sleep(100)
            emitter.onNext(3)
            Thread.sleep(1000)
            emitter.onNext(4)
            Thread.sleep(100)
            emitter.onComplete()
        }.subscribeOn(ThreadScheduler())

        val observer = TestObserver<Int>()
        val disposable = observable.subscribe(observer)

        Thread.sleep(300)
        disposable.dispose()
        Thread.sleep(1500)

        observer
            .waitForFinished()
            .assertCount(2)
            .assertAtIndex(0, 1)
            .assertAtIndex(0, 2)
            .assertCompletion()
    }

}