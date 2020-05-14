package com.dvpermyakov.rx.shedulers

import java.util.concurrent.Executors

class ThreadScheduler : Scheduler {
    var lastExecutedThread: Thread? = null
        private set

    private val executor = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable).also { thread ->
            this@ThreadScheduler.lastExecutedThread = thread
        }
    }

    override fun schedule(task: Runnable) {
        executor.submit(task)
    }
}