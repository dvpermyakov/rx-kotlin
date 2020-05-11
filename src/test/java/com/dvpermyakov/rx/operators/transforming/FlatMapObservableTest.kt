package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.operators.creating.just
import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import com.dvpermyakov.rx.utils.getOrderObservable
import com.dvpermyakov.rx.utils.getStringByOrderObservable
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

    @Test(timeout = 10000L)
    fun flatMapAsync() {
        val observer = TestObserver<String>()
        getOrderObservable()
            .flatMap { value ->
                getStringByOrderObservable(value).subscribeOn(ThreadScheduler())
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
}