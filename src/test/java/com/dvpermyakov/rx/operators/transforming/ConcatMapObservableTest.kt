package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import com.dvpermyakov.rx.utils.getOrderObservable
import com.dvpermyakov.rx.utils.getStringByOrderObservable
import org.junit.Test

class ConcatMapObservableTest {

    @Test(timeout = 10000L)
    fun concatMapAsync() {
        val observer = TestObserver<String>()
        getOrderObservable()
            .concatMap { value ->
                getStringByOrderObservable(value).subscribeOn(ThreadScheduler())
            }
            .subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(10)
            .assertAtIndex(0, "first_1")
            .assertAtIndex(1, "first_2")
            .assertAtIndex(2, "first_3")
            .assertAtIndex(3, "second_1")
            .assertAtIndex(4, "second_2")
            .assertAtIndex(5, "third_1")
            .assertAtIndex(6, "third_2")
            .assertAtIndex(7, "third_3")
            .assertAtIndex(8, "third_4")
            .assertAtIndex(9, "third_5")
            .assertCompletion()
    }

}