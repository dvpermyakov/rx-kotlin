package com.example.rxjava.functions

interface MapFunction<T> {
    fun map(item: T): T
}