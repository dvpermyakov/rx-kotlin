package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.operators.utility.subscribeOn
import com.dvpermyakov.rx.shedulers.ThreadScheduler
import com.dvpermyakov.rx.utils.TestObserver
import com.dvpermyakov.rx.utils.getOrderObservable
import com.dvpermyakov.rx.utils.getStringByOrderObservable
import org.junit.Test

class SwitchMapObservableTest {

    @Test
    fun switchMapAsync() {
        val observer = TestObserver<String>()
        getOrderObservable()
            .switchMap { value ->
                getStringByOrderObservable(value).subscribeOn(ThreadScheduler())
            }
            .subscribe(observer)

        observer
            .waitForFinished()
            .assertCount(7)
            .assertAtIndex(0, "first_1")
            .assertAtIndex(1, "second_1")
            .assertAtIndex(2, "third_1")
            .assertAtIndex(3, "third_2")
            .assertAtIndex(4, "third_3")
            .assertAtIndex(5, "third_4")
            .assertAtIndex(6, "third_5")
            .assertCompletion()
    }

}