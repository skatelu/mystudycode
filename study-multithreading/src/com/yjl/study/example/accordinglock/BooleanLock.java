package com.yjl.study.example.accordinglock;

import java.util.Collection;

public class BooleanLock implements Lock{
    //The initValue is true indicated the lock have be get.
    //The initValue is false indicated the lock is free (other thread can get this);
    private boolean initValue;

    @Override
    public void lock() throws InterruptedException {

    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeOutException {

    }

    @Override
    public void unlock() {

    }

    @Override
    public Collection<Thread> getBlockedThread() {
        return null;
    }

    @Override
    public int getBlockedSize() {
        return 0;
    }
}
