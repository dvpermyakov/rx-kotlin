package com.dvpermyakov.rx.utils

import com.dvpermyakov.rx.observers.Observer
import org.junit.Assert
import java.lang.IllegalStateException


class TestObserver<T> : Observer<T> {
    private var state: Observer.State = Observer.State.Idle
    private val list = mutableListOf<T>()

    override fun onNext(item: T) {
        if (state in listOf(Observer.State.Idle, Observer.State.Subscribed)) {
            state = Observer.State.Subscribed
            list.add(item)
        } else {
            throw IllegalStateException("onNext with item ($item) in state ($state)")
        }
    }

    override fun onComplete() {
        state = Observer.State.Completed
    }

    override fun onError(t: Throwable) {
        state = Observer.State.Error(t)
    }

    fun assertIdle(): TestObserver<T> {
        Assert.assertEquals(state, Observer.State.Idle)
        return this
    }

    fun assertIdleOrSubscribed(): TestObserver<T> {
        Assert.assertTrue(
            state in listOf(Observer.State.Idle, Observer.State.Subscribed)
        )
        return this
    }

    fun assertCompletion(): TestObserver<T> {
        Assert.assertEquals(state, Observer.State.Completed)
        return this
    }

    fun assertError(error: Throwable): TestObserver<T> {
        Assert.assertEquals(state, Observer.State.Error(error))
        return this
    }

    fun assertCount(count: Int): TestObserver<T> {
        Assert.assertEquals(list.size, count)
        return this
    }

    fun assertAtIndex(index: Int, value: T): TestObserver<T> {
        Assert.assertEquals(list[index], value)
        return this
    }
}