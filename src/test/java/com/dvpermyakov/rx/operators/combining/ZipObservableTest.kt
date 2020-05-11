package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.functions.ZipperWithTwo
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class ZipObservableTest {

    @Test
    fun zip() {
        val observableInt = Observable.create<Int> { emitter ->
            Thread.sleep(50L)
            emitter.onNext(1)
            Thread.sleep(100L)
            emitter.onNext(2)
            Thread.sleep(30L)
            emitter.onNext(3)
            emitter.onComplete()
        }

        val observableString = Observable.create<String> { emitter ->
            Thread.sleep(10L)
            emitter.onNext("a")
            Thread.sleep(20L)
            emitter.onNext("b")
            Thread.sleep(10L)
            emitter.onNext("c")
            Thread.sleep(80L)
            emitter.onNext("e")
            Thread.sleep(100L)
            emitter.onNext("f")
            emitter.onComplete()
        }

        val observer = TestObserver<ZipPair>()
        Observable.zip(
            observableInt.subscribeOn(ThreadScheduler()),
            observableString.subscribeOn(ThreadScheduler()),
            object : ZipperWithTwo<Int, String, ZipPair> {
                override fun apply(item1: Int, item2: String) = ZipPair(item1, item2)
            }
        ).subscribeOn(ThreadScheduler()).subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(3)
            .assertAtIndex(0, ZipPair(1, "a"))
            .assertAtIndex(1, ZipPair(2, "b"))
            .assertAtIndex(2, ZipPair(3, "c"))
            .assertCompletion()
    }

    data class ZipPair(val int: Int, val string: String)
}