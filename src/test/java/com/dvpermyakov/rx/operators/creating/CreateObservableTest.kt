package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.emitter.Emitter
import com.dvpermyakov.rx.emitter.EmitterSource
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.utils.TestObserver
import com.dvpermyakov.rx.operators.create
import org.junit.Test
import java.lang.IllegalArgumentException

class CreateObservableTest {

    @Test
    fun createWithCompletion() {
        val observer = TestObserver<Int>()
        Observable.create(object : EmitterSource<Int> {
            override fun subscribe(emitter: Emitter<Int>) {
                emitter.onNext(10)
                emitter.onNext(5)
                emitter.onNext(7)
                emitter.onComplete()
            }

        }).subscribe(observer)

        observer
            .assertCount(3)
            .assertAtIndex(0, 10)
            .assertAtIndex(1, 5)
            .assertAtIndex(2, 7)
            .assertCompletion()
    }

    @Test
    fun createWithError() {
        val error = IllegalArgumentException()
        val observer = TestObserver<Int>()
        Observable.create(object : EmitterSource<Int> {
            override fun subscribe(emitter: Emitter<Int>) {
                emitter.onNext(8)
                emitter.onError(error)
            }

        }).subscribe(observer)

        observer
            .assertCount(1)
            .assertAtIndex(0, 8)
            .assertError(error)
    }
}