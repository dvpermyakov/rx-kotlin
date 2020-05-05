package com.dvpermyakov.rx.functions

interface Function {
    fun action()
}

fun (() -> Unit).toFunction(): Function {
    return object : Function {
        override fun action() {
            invoke()
        }
    }
}

interface ApplyFunction<T> {
    fun apply(item: T)
}

fun <T> ((T) -> Unit).toFunction(): ApplyFunction<T> {
    return object : ApplyFunction<T> {
        override fun apply(item: T) {
            invoke(item)
        }
    }
}