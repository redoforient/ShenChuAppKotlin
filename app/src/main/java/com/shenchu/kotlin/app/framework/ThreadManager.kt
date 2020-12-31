package com.shenchu.kotlin.app.framework

import java.util.concurrent.*

/**
 * @author zhangll
 * @description
 * @date 2020/11/26 10:03
 */
class ThreadManager private constructor() {
    //处理中心
    private lateinit var mThreadPoolExecutor: ExecutorService;

    //定义一个请求队列-阻塞
    private val mQueue: BlockingQueue<Runnable> =
        LinkedBlockingDeque()

    //添加任务
    fun addTask(runnable: Runnable?) {
        if (runnable == null) {
            return
        }
        mQueue.add(runnable)
    }

    /**
     * 核心线程
     */
    private val coreTask = Runnable {
        while (true) {
            try {
                val task = mQueue.take() //如果没有任务，则在此处阻塞
                mThreadPoolExecutor.execute(task)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        val instance = ThreadManager()
    }

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(3,
            3, 3, TimeUnit.SECONDS,
            ArrayBlockingQueue(4),
            RejectedExecutionHandler { r, executor -> //参数r，就是出问题丢出来的任务
                addTask(r)
            })
        mThreadPoolExecutor.execute(coreTask)
    }
}