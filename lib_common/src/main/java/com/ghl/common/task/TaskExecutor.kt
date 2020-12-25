package com.ghl.common.task

import com.ghl.common.ITask
import java.util.concurrent.BlockingQueue

class TaskExecutor(var taskQueue: BlockingQueue<ITask>) : Thread() {
    private var isRunning = true

    //退出
    fun quit() {
        isRunning = false
        interrupt()
    }

    override fun run() {
        while (isRunning) {
            val take: ITask
            try {
                take = taskQueue.take()
            } catch (e: InterruptedException) {
                if (!isRunning) {
                    interrupt()
                    break
                }
                continue
            }
            take.run()
        }
    }
}