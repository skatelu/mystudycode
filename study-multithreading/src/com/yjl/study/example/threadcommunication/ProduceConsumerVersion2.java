package com.yjl.study.example.threadcommunication;

import java.util.Optional;
import java.util.stream.Stream;

/**
 *  正确的多个创建者，多个消费者模式
 */
public class ProduceConsumerVersion2 {
    private int i = 1;

    private volatile boolean isProduced = false;

    final Object LOCK = new Object();
    private void produce(){

        synchronized (LOCK){
            while (isProduced){
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("P->"+ i++);
            LOCK.notifyAll();
            isProduced =true;

        }
    }

    private void consume(){
        synchronized (LOCK){
            while (!isProduced){
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("C->"+ i);
            LOCK.notifyAll();
            isProduced = false;
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion2 pc = new ProduceConsumerVersion2();

        Stream.of("P1","P2","P3").forEach(n ->
                new Thread(n){
                    @Override
                    public void run() {
                        while (true){
                            pc.produce();
                        }
                    }
                }.start()
        );

        Stream.of("C1","C2","C3").forEach(n ->
                new Thread(n){
                    @Override
                    public void run() {
                        while (true){
                            pc.consume();
                        }
                    }
                }.start()
        );
    }
}
