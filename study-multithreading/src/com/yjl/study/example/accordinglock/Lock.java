package com.yjl.study.example.accordinglock;

import java.sql.Time;
import java.util.Collection;
import java.util.Collections;

/**
 * 显示锁
 */
public interface Lock {

    public static class TimeOutException extends Exception{
        public TimeOutException(String message){
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException,TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();
}
