package com.mingrn.keeper.msg.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置,覆盖springBoot默认异步线程池
 *
 * @author MinGRn <br > MinGRn97@gmail.com
 * @date 2019-02-21 09:38
 */
@Configuration
public class AsycTaskConfig implements AsyncConfigurer {

    /**
     * 机器线程核心数
     */
    private static int AVAILABLE_PROCESSORS;

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfigurer.class);

    static {
        AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();
        LOGGER.info("the machine available cpu processors: {}", AVAILABLE_PROCESSORS);
    }


    /**
     * 对拒绝 task 的处理策略:
     * ③ ThreadPoolExecutor.DiscardPolicy 不能执行的任务将被删除
     * ① ThreadPoolExecutor.AbortPolicy 默认处理策略,处理程序遇到拒绝将抛出运行时 RejectedExecutionException
     * ② ThreadPoolExecutor.CallerRunsPolicy 程序调用运行该任务的 execute 本身。此策略提供简单的反馈控制机制,能够减缓新任务提交速度
     * ④ ThreadPoolExecutor.DiscardOldestPolicy 如果程序尚未关闭,则位于工作列表头部的任务将删除,然后重新执行程序(如果再次失败,则重复此过程)
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 线程池维护守护线程最少数量 = 机器CPU核心数
        taskExecutor.setCorePoolSize(AVAILABLE_PROCESSORS);
        // 线程池维护线程最大数量
        taskExecutor.setMaxPoolSize(AVAILABLE_PROCESSORS * 2);
        // 缓存队列数量,默认值为 {@code Integer.MAX_VALUE}
        taskExecutor.setQueueCapacity(999);
        // 对拒绝 task 的处理策略
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程名前缀
        taskExecutor.setThreadNamePrefix("async-task-thread-");
        // 初始化线程池
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
