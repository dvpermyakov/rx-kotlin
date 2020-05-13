package com.dvpermyakov.rx.subjects

import com.dvpermyakov.rx.utils.TestObserver
import org.junit.Test

class BehaviourSubjectTest {

    @Test
    fun behaviourSubject() {
        val subject = BehaviourSubject<Int>()
        subject.onNext(1)
        subject.onNext(2)
        subject.onNext(3)

        val observer1 = TestObserver<Int>()
        subject.subscribe(observer1)

        subject.onNext(4)

        val observer2 = TestObserver<Int>()
        subject.subscribe(observer2)

        subject.onNext(5)
        subject.onComplete()

        observer1
            .assertCompletion()
            .assertCount(3)
            .assertAtIndex(0, 3)
            .assertAtIndex(1, 4)
            .assertAtIndex(2, 5)

        observer2
            .assertCompletion()
            .assertCount(2)
            .assertAtIndex(0, 4)
            .assertAtIndex(1, 5)
    }

}