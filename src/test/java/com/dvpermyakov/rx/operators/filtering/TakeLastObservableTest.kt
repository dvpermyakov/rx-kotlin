package com.dvpermyakov.rx.operators.filtering

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.observer.TestObserver
import com.dvpermyakov.rx.operators.fromList
import com.dvpermyakov.rx.operators.takeLast
import org.junit.Test

class TakeLastObservableTest {

    @Test
    fun takeLast() {
        val count = 5
        val values = listOf(0, 0, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 6, 7, 8, 8, 9)
        val observer = TestObserver<Int>()
        Observable
            .fromList(values)
            .takeLast(count)
            .subscribe(observer)

        observer
            .assertCount(count)
            .assertAtIndex(0, values[values.lastIndex - 4])
            .assertAtIndex(1, values[values.lastIndex - 3])
            .assertAtIndex(2, values[values.lastIndex - 2])
            .assertAtIndex(3, values[values.lastIndex - 1])
            .assertAtIndex(4, values[values.lastIndex])
            .assertCompletion()
    }

}