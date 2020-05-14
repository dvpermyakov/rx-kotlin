package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Assert
import org.junit.Test

class DoOnNextObservableTest {

    @Test
    fun doOnNext() {
        val values = listOf(10, 20, 30)

        var index = 0
        val observer = TestObserver<Int>()
        Observable
            .fromList(values)
            .doOnNext { value ->
                observer.assertSubscribed()
                Assert.assertEquals(value, values[index++])
            }
            .subscribe(observer)

        observer.waitForFinished()
    }

}