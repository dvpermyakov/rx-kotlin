package com.dvpermyakov.rx.functions

interface Consumer<T> {
    fun accept(item: T)
}

fun <T> ((T) -> Unit).toConsumer(): Consumer<T> {
    return object : Consumer<T> {
        override fun accept(item: T) {
            invoke(item)
        }
    }
}