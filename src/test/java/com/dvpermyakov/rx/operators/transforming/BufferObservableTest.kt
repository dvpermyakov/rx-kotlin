package com.dvpermyakov.rx.operators.transforming

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Ignore
import org.junit.Test

class BufferObservableTest {

    @Ignore
    @Test
    fun buffer() {
        val v1 = listOf(10, 20, 30)
        val v2 = listOf(40, 50, 60)
        val v3 = listOf(70, 80)

        val observer = TestObserver<List<Int>>()
        Observable
            .fromList(v1 + v2 + v3)
            .buffer(3)
            .subscribe(observer)

        observer.assertCount(3)
            .assertAtIndex(0, v1)
            .assertAtIndex(1, v2)
            .assertAtIndex(2, v3)
            .assertCompletion()
    }

}