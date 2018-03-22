package com.go.junitTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hboxs021 on 2018/3/22.
 */
public class interview extends Thread {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行、、、");
    }

    public static void main(String[] arg) {
        //创建线程池,单一线程，只允许一个线程执行
//        ExecutorService pool = Executors.newSingleThreadExecutor();
        //创建可重用线程池
//        ExecutorService pool = Executors.newFixedThreadPool(2);
        //创建可缓存线程池
//        ExecutorService pool = Executors.newCachedThreadPool();
        //创建无限量线程池
        /*ExecutorService pool = Executors.newScheduledThreadPool(2);


        Thread thread1 = new interview();
        thread1.setName("thread1");
        Thread thread2 = new interview();
        thread2.setName("thread2");
        Thread thread3 = new interview();
        thread3.setName("thread3");
        Thread thread4 = new interview();
        thread4.setName("thread4");

        pool.execute(thread1);
        pool.execute(thread2);
        pool.execute(thread3);
        pool.execute(thread4);

        pool.shutdown();*/
        String str1 = "hello";
        String str2 = "hel"+"lo";
        System.out.println(str1==str2);
    }
}
