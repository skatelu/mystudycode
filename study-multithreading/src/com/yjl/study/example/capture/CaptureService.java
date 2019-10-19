package com.yjl.study.example.capture;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * 综合案例：通知机制综合运用
 *      实现数据采集
 *      模仿场景：有10台机器，现在希望一直保持有5个线程同时运行
 *      当有一个线程退出时，另外一个线程加入进去
 */
public class CaptureService {

    final static LinkedList<Control> CONTROLS = new LinkedList<>();
    private final static int MAX_WORKER = 5;

    public static void main(String[] args) {

        List<Thread> worker = new ArrayList<>();
        Arrays.asList("M1","M2","M3","M4","M5","M6","M7","M8","M9","M10").stream()
                .map(CaptureService::createCaptureThread)
                .forEach(t->{
                    t.start();
                    worker.add(t);
                });
        worker.stream().forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("All of capture work finished").ifPresent(System.out::println);
    }

    private static Thread createCaptureThread(String name){

        return new Thread(()->{
            Optional.of("The worker [" + Thread.currentThread().getName() + "] begin capture data")
            .ifPresent(System.out::println);
            synchronized (CONTROLS){
                while (CONTROLS.size() > MAX_WORKER){
                    try {
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROLS.add(new Control());
            }

            Optional.of("The Worker ["+Thread.currentThread().getName()+"] is working...")
                    .ifPresent(System.out::println);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (CONTROLS){
                Optional.of("The Worker ["+Thread.currentThread().getName()+"] END capture data")
                        .ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }

        },name);
    }

    private static class Control{

    }
}
