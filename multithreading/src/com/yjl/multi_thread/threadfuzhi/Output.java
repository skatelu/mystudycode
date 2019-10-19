package com.yjl.multi_thread.threadfuzhi;

/**
 *    输出线程，对资源对象Resource中成员变量，输出值
 */
public class Output implements Runnable{
    private Resource r;

    public Output(Resource r){
        this.r = r;
    }

    @Override
    public void run() {
        while(true){
            synchronized (r){
                if(!r.flag){
                    try{r.wait();}catch (Exception e){}
                }
                System.out.println(r.name + "..." + r.sex);
                //将表及改成false，唤醒对方线程
                r.flag = false;
                r.notify();
            }
        }
    }
}
