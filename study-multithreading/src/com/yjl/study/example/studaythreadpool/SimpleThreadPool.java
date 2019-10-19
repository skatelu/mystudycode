package com.yjl.study.example.studaythreadpool;

import javafx.concurrent.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 线程池的相关概念：
 *      任务队列
 *      拒绝策略（抛出异常，直接丢弃，阻塞，临时队列）
 *      初始化值 init
 *      使用值 active
 *      max 最大值
 *      闲置的时候默认值
 *
 *      min >= active <= max
 */
public class SimpleThreadPool {

    private final int size; //线程池中线程的数量
    private final int queueSize; //缓存任务队列中默认队列的长度

    private final static int DEFAULT_SIZE = 10;//默认线程池中线程的数量

    private final static int DEFAULT_TASK_QUEUE_SIZE = 2000;

    private static volatile int seq = 0;

    private final static String THREAD_PREFIX = "SIMPLE_THREAD_POOL-"; //定义线程前缀

    private final static ThreadGroup GROUP = new ThreadGroup("Pool_Group");//定义Thread_group名称

    private static final LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();//创建任务队列 存放未运行的任务,达到缓冲目的

    private final static List<WorkerTask> THREAD_QUEUE = new ArrayList<>(); //存放start() 方法运行后的任务

    private final DiscardPolicy discardPolicy;

    //定义初始化缓存任务队列策略  java8 lamdba 表达式 函数式编程匿名内部类
    public final static DiscardPolicy DEFAULT_DISCARD_POLICY = ()->{
        throw new DiscardException("Discard this Task;");
    };

    public SimpleThreadPool(){
        this(DEFAULT_SIZE,DEFAULT_TASK_QUEUE_SIZE,DEFAULT_DISCARD_POLICY);
    }

    public SimpleThreadPool(int size,int queueSize,DiscardPolicy discardPolicy) {
        this.size = size;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
        init();
    }

//    初始化线程池
    private void init(){
        for (int i = 0; i < size; i++) {
            createWorkTask();
        }
    }

    /**
     * 对外暴露接口，就是获取要执行的任务
     * @param runnable
     */
    public void submit(Runnable runnable){
        synchronized (TASK_QUEUE){
            if(TASK_QUEUE.size()>queueSize){
                discardPolicy.discard();
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();//唤醒等待线程
        }
    }

    private void createWorkTask(){
        //创建自定义线程 WorkerTask
        WorkerTask task = new WorkerTask(GROUP, THREAD_PREFIX + (seq++));
        task.start();
        THREAD_QUEUE.add(task);
    }

    //定义一个枚举类，设置当前线程状态
    private enum TaskState{
        FREE,RUNNING,BLICKED,DEAD
    }

    //自定义运行异常
    public static class DiscardException extends RuntimeException{
        public DiscardException(String message){
            super(message);
        }
    }

    //自定义策略，当缓存队列超过最大值的时候，应该怎么做   因为需要给外部实现，定义成public就好
    public interface DiscardPolicy{
        void discard() throws DiscardException;
    }

    //自定义一个的线程
    private static class WorkerTask extends Thread{
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask(ThreadGroup group,String name){
            super(group,name);
        }

        public TaskState getTaskState(){
            return this.taskState;
        }

        /**
         * 重写run方法，防止线程运行完成之后就自动杀死自己
         */
        @Override
        public void run(){
            OUTER:
            while (this.taskState != TaskState.DEAD) {
                Runnable runnable;
                //从人物队列中获取任务
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()){
                        try {
                            taskState = TaskState.BLICKED;
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            break OUTER;
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();//取出最头上的一个线程任务
                }
                if(runnable != null){
                    taskState = TaskState.RUNNING;
                    runnable.run();
                    taskState = TaskState.FREE;
                }
            }
        }

        public void close(){
            this.taskState = TaskState.DEAD;
        }
    }

    public static void main(String[] args) {
        SimpleThreadPool threadPool = new SimpleThreadPool(6,10,SimpleThreadPool.DEFAULT_DISCARD_POLICY);//创建线程池
        IntStream.rangeClosed(0, 40)
            .forEach(i->{
                threadPool.submit(()->{
                    System.out.println("The runnable"+i+"be serviced by "+Thread.currentThread().getName()+" --- start ---");
                    try {
                        Thread.sleep(1_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("The runnable " + i + " be serviced by " + Thread.currentThread().getName()+" --- end ---");
                });
            });

    }
}
