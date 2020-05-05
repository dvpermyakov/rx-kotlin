package com.dvpermyakov.rx.functions

interface Callable<T> {
    fun call(): T
}

fun <T> (() -> T).toCallable(): Callable<T> {
    return object : Callable<T> {
        override fun call(): T {
            return invoke()
        }
    }
}