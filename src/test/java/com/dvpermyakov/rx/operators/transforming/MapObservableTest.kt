package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class MapObservableTest {

    @Test
    fun mapInt() {
        val values = listOf(10, 20, 30)
        val factor = 2
        val observer = TestObserver<Int>()
        Observable
            .fromList(values)
            .map { value -> value * factor }
            .subscribe(observer)

        observer
            .assertCount(values.size)
            .assertAtIndex(0, values[0] * factor)
            .assertAtIndex(1, values[1] * factor)
            .assertAtIndex(2, values[2] * factor)
            .assertCompletion()
    }

}