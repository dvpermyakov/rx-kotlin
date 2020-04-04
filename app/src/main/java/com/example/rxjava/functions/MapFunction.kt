package com.example.rxjava.functions

interface MapFunction<T> {
    fun map(item: T): T
}

fun <T> ((T) -> T).toFunction(): MapFunction<T> {
    return object : MapFunction<T> {
        override fun map(item: T): T {
            return invoke(item)
        }
    }
}