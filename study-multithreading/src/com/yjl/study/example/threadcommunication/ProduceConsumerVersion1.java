package com.yjl.study.example.threadcommunication;

/**
 * 如下个例子所示，线程间没有进行通讯，当producer不断生产数据的时候，consume也在不断的消费数据
 * 但会出现不是最新产生的数据在一直消费的问题，即生产者生产了最新的数据，但是消费者不一定消费到
 */
public class ProduceConsumerVersion1 {
    private int i = 1;

    final Object LOCK = new Object();
    private void produce(){
        synchronized (LOCK){
            System.out.println("P->"+ i++);
        }
    }

    private void consume(){
        synchronized (LOCK){
            System.out.println("C->"+ i);
        }
    }

    public static void main(String[] args) {
        ProduceConsumerVersion1 pc = new ProduceConsumerVersion1();

        new Thread("P"){
            @Override
            public void run() {
                while (true){
                    pc.produce();
                }
            }
        }.start();

        new Thread("C"){
            @Override
            public void run() {
                while (true){
                    pc.consume();
                }
            }
        }.start();
    }
}
