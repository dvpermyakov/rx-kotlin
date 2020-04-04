package com.example.rxjava.functions

interface MapFunction<T, R> {
    fun map(item: T): R
}

fun <T, R> ((T) -> R).toFunction(): MapFunction<T, R> {
    return object : MapFunction<T, R> {
        override fun map(item: T): R {
            return invoke(item)
        }
    }
}