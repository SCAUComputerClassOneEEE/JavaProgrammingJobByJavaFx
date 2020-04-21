package util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
线程池
加载图片,网络连接,文件传输,文件加载......
 */
public class TaskThreadPool {
    private static final int QUEUE_SIZE = 5;//缓冲队列队长
    private static final int ALIVE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 6;
    private static final int KEEP_ALIVE_TIME = 6;
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(
            ALIVE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,//时间单位
            new LinkedBlockingDeque<>(QUEUE_SIZE),//阻塞缓冲队列
            new TaskThreadFactory()//处理方法，设置为守护线程
    );

    public static void execute(Runnable runnable) {
        THREAD_POOL.execute(runnable);
    }

    public static final class TaskThreadFactory implements ThreadFactory {

        private static final String NAME_PREFIX = "task-pool-thread-";
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, NAME_PREFIX + threadNumber.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    }
}
