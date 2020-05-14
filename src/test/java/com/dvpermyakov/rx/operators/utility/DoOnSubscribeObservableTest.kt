package com.dvpermyakov.rx.operators.utility

import com.dvpermyakov.rx.observables.Observable
import com.dvpermyakov.rx.operators.creating.fromList
import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class DoOnSubscribeObservableTest {

    @Test
    fun doOnSubscribe() {
        val values = listOf(10, 20, 30)

        val observer = TestObserver<Int>()
        Observable
            .fromList(values)
            .doOnSubscribe {
                observer.assertSubscribed()
            }
            .subscribe(observer)

        observer.waitForFinished()
    }

}