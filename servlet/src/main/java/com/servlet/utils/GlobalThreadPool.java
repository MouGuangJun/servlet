package com.servlet.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 项目全局使用的线程池
 */
public class GlobalThreadPool {

    private static final ExecutorService executor = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors());// 固定大小的线程池

    /**
     * 获取线程池执行器
     *
     * @return 线程池执行器
     */
    public static ExecutorService getExecutor() {
        return executor;
    }

    /**
     * 获取阻塞队列个数
     *
     * @return 阻塞队列个数
     */
    public static int getBlockingQueueSize() {
        BlockingQueue<Runnable> queue = ((ThreadPoolExecutor) getExecutor()).getQueue();

        return queue.isEmpty() ? 0 : queue.size();
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        getExecutor().shutdown();
    }
}
