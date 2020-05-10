package com.dvpermyakov.rx.operators.filtering

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class FilterObservableTest {

    @Test
    fun filter() {
        val values = listOf(0, 0, 1, 2, 2, 3, 3, 4, 4, 4, 4, 5, 6, 7, 8, 8, 9)
        val observer = TestObserver<Int>()
        Observable
            .fromList(values)
            .filter { value -> value % 2 == 0 }
            .subscribe(observer)

        observer
            .assertCount(11)
            .assertAtIndex(0, 0)
            .assertAtIndex(1, 0)
            .assertAtIndex(2, 2)
            .assertAtIndex(3, 2)
            .assertAtIndex(4, 4)
            .assertAtIndex(5, 4)
            .assertAtIndex(6, 4)
            .assertAtIndex(7, 4)
            .assertAtIndex(8, 6)
            .assertAtIndex(9, 8)
            .assertAtIndex(10, 8)
            .assertCompletion()
    }

}