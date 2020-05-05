package com.dvpermyakov.rx.functions

interface Zipper<T, R> {
    fun apply(items: List<T>): R
}

@Suppress("UNCHECKED_CAST")
interface ZipperWithTwo<T1 : Any, T2 : Any, R> : Zipper<Any, R> {
    fun apply(item1: T1, item2: T2): R

    override fun apply(items: List<Any>): R {
        return apply(items[0] as T1, items[1] as T2)
    }
}