package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.create
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.operators.creating.just
import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class FlatMapObservableTest {

    @Test
    fun flatMapSimple() {
        val values = listOf(10, 20, 30)
        val factor = 2

        val observer = TestObserver<Int>()
        Observable
            .fromList(values)
            .flatMap { value ->
                Observable.just(value * factor)
            }
            .subscribe(observer)

        observer
            .assertCount(values.size)
            .assertAtIndex(0, values[0] * factor)
            .assertAtIndex(1, values[1] * factor)
            .assertAtIndex(2, values[2] * factor)
            .assertCompletion()
    }

    @Test
    fun flatMapAsync() {
        val observableInt = Observable.create<Order> { emitter ->
            emitter.onNext(Order.First)
            Thread.sleep(10L)
            emitter.onNext(Order.Second)
            Thread.sleep(20L)
            emitter.onNext(Order.Third)
            Thread.sleep(5000L)
            emitter.onComplete()
        }.subscribeOn(ThreadScheduler())

        val observer = TestObserver<String>()
        observableInt
            .flatMap { value ->
                getObservableString(value).subscribeOn(ThreadScheduler())
            }
            .subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(10)
            .assertAtIndex(0, "first_1")
            .assertAtIndex(1, "second_1")
            .assertAtIndex(2, "third_1")
            .assertAtIndex(3, "third_2")
            .assertAtIndex(4, "second_2")
            .assertAtIndex(5, "third_3")
            .assertAtIndex(6, "first_2")
            .assertAtIndex(7, "third_4")
            .assertAtIndex(8, "first_3")
            .assertAtIndex(9, "third_5")
            .assertCompletion()
    }

    sealed class Order {
        object First : Order()
        object Second : Order()
        object Third : Order()
    }

    private fun getObservableString(order: Order): Observable<String> {
        return when (order) {
            Order.First -> Observable.create { emitter ->
                emitter.onNext("first_1")  // 0L
                Thread.sleep(100L)
                emitter.onNext("first_2")  // 100L
                Thread.sleep(20L)
                emitter.onNext("first_3")  // 120L
                emitter.onComplete()
            }
            Order.Second -> Observable.create { emitter ->
                emitter.onNext("second_1")  // 10L
                Thread.sleep(40)
                emitter.onNext("second_2")  // 50L
                emitter.onComplete()
            }
            Order.Third -> Observable.create { emitter ->
                emitter.onNext("third_1")  // 20L
                Thread.sleep(20L)
                emitter.onNext("third_2")  // 40L
                Thread.sleep(20L)
                emitter.onNext("third_3")  // 60L
                Thread.sleep(50L)
                emitter.onNext("third_4")  // 110L
                Thread.sleep(20L)
                emitter.onNext("third_5")  // 140L
                emitter.onComplete()
            }
        }
    }
}