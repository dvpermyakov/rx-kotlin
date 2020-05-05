package com.dvpermyakov.rx.shedulers

import java.util.concurrent.Executors

class ThreadScheduler : Scheduler {
    private val executor = Executors.newFixedThreadPool(1)

    override fun schedule(task: Runnable) {
        executor.submit(task)
    }
}