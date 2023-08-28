package org.example;

import org.springframework.util.StopWatch;

import java.io.Closeable;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huen on 2023/7/24 14:25
 */
public class MainTest implements Closeable {
    public Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    static ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 20, 20, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(20));

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("main start ...");
        try (MainTest ignored = new MainTest()) {
            join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.getTotalTimeMillis()+"ms");
    }


    public static void reentrantLock(){

    }

    public static void thenRun() {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("thread name:" + Thread.currentThread().getName() + " first step...");
            return 2;
        }, executor).thenComposeAsync(MainTest::thenComposeAsync).thenApply(e -> {
            System.out.println("thread name:" + Thread.currentThread().getName() + " third step...");
            return e + 9;
        });
        System.out.println("------------------- " + completableFuture.join());
    }

    public static CompletableFuture<Integer> thenComposeAsync(Integer d) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("thread name:" + Thread.currentThread().getName() + " second step...");
            return d + 3;
        },executor);
    }

    public static void join() {
        int[] s;
        try (MainTest mainTest = new MainTest()) {
            s = completableFuture();
            Arrays.stream(s).forEach(System.out::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] completableFuture() {
        final int[] cc = new int[3];

        CompletableFuture<int[]> task3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cc[2] = 3;
            return cc;
        }, executor);

        CompletableFuture<int[]> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int a = 10 / 0;
            cc[0] = 1;
            return cc;
        }, executor).handleAsync((v, e) -> {
            if (e != null) {
                throw new RuntimeException("CompletableFuture处理异常 -> " + e.getMessage());
            }
            return v;
        });

        CompletableFuture<int[]> task2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cc[1] = 2;
            return cc;
        }, executor);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(task2, task1, task3);
        allOf.join();
        return cc;
    }


    @Override
    public void close() {
        System.out.println("shutdown");
        executor.shutdown();
    }
}
