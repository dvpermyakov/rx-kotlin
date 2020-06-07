package com.dvpermyakov.rx.operators.combining

import com.dvpermyakov.rx.functions.MapFunction
import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class CombineLatestObservableTest {

    @Test(timeout = 10000L)
    fun combineLatest() {
        val observableInt = Observable.create<Int> { emitter ->
            Thread.sleep(50L)
            emitter.onNext(1)  // 50l
            Thread.sleep(100L)
            emitter.onNext(2)  // 150l
            Thread.sleep(30L)
            emitter.onNext(3)  // 180l
            emitter.onComplete()
        }

        val observableString = Observable.create<String> { emitter ->
            Thread.sleep(10L)
            emitter.onNext("a")  // 10l
            Thread.sleep(20L)
            emitter.onNext("b")  // 30l
            Thread.sleep(10L)
            emitter.onNext("c")  // 40l
            Thread.sleep(80L)
            emitter.onNext("e")  // 120l
            Thread.sleep(100L)
            emitter.onNext("f")  // 220l
            emitter.onComplete()
        }

        val observer = TestObserver<CombineLatestPair>()
        Observable.combineLatest(
            observableInt.subscribeOn(ThreadScheduler()),
            observableString.subscribeOn(ThreadScheduler()),
            object : MapFunction<List<Any>, CombineLatestPair> {
                override fun map(item: List<Any>): CombineLatestPair {
                    return CombineLatestPair(
                        int = item[0] as Int,
                        string = item[1] as String
                    )
                }

            }
        ).subscribeOn(ThreadScheduler()).subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(4)
            .assertAtIndex(0, CombineLatestPair(1, "c"))
            .assertAtIndex(1, CombineLatestPair(1, "e"))
            .assertAtIndex(2, CombineLatestPair(2, "e"))
            .assertAtIndex(3, CombineLatestPair(3, "e"))
            .assertCompletion()
    }

    data class CombineLatestPair(val int: Int, val string: String)

}