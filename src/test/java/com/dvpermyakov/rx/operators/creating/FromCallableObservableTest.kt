package com.dvpermyakov.rx.operators.creating

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class FromCallableObservableTest {

    @Test
    fun fromCallable() {
        var value: Data? = null
        val observer = TestObserver<Data>()
        Observable.fromCallable {
            Data(10, 10).also { data ->
                value = data
            }
        }.subscribe(observer)

        observer
            .assertCount(1)
            .assertAtIndex(0, value!!)
            .assertCompletion()
    }

    data class Data(val x: Int, val y: Int)
}