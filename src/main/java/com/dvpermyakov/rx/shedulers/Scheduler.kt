package com.dvpermyakov.rx.shedulers

interface Scheduler {
    fun schedule(task: Runnable)
}