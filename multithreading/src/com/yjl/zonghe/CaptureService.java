package com.yjl.zonghe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 *  综合项目，数据采集
 *  目的 保证每次最多只有五个线程在执行任务
 */
public class CaptureService {

    final static private LinkedList<Control> CONTROLS = new LinkedList<>();
    private final static int MAX_WORKER = 5;
    public static void main(String[] args) {
        List<Thread> worker = new ArrayList<>();
        Arrays.asList("M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10").stream()
        .map(CaptureService::createCaptureThread)
        .forEach(t ->{
            t.start();
            worker.add(t);
        });

        worker.stream().forEach(t ->{
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
                    while (CONTROLS.size() >= MAX_WORKER){
                        try {
                            CONTROLS.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    CONTROLS.addLast(new Control());
                }

                Optional.of("The worker [" + Thread.currentThread().getName() + "] is workering...")
                        .ifPresent(System.out::println);
            try {
                Thread.sleep(20_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (CONTROLS){
                Optional.of("The worker [" + Thread.currentThread().getName() + "] END capture data.")
                        .ifPresent(System.out::println);
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        },name);
    }

    private static class Control{

    }
}
