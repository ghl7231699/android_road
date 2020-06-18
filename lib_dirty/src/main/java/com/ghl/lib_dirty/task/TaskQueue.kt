package com.ghl.lib_dirty.task

import com.ghl.lib_dirty.ITask
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class TaskQueue(var size: Int = 3) {
        var mQueue: BlockingQueue<ITask> = LinkedBlockingQueue()
        private val mTaskExecutors = arrayOfNulls<TaskExecutor>(size)
        fun start() {
            stop()
            for (index in mTaskExecutors.indices) {
                mTaskExecutors[index] = TaskExecutor(mQueue)
                mTaskExecutors[index]?.start()
            }
        }

        fun stop() {
            mTaskExecutors.apply {
                for (executor in mTaskExecutors) {
                    executor?.apply {
                        executor.quit()
                    }
                }
            }
        }

        fun <T : ITask?> add(task: T): Int {
            if (!mQueue.contains(task)) {
                mQueue.add(task)
            }

            return mQueue.size
        }

    }