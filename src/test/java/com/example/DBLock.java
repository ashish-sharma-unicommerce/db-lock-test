package com.example;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import com.example.bean.DatabaseLock;
import com.example.dao.DBLockDao;

/**
 * @author Ashish Sharma on 08/12/19.
 */
public class DBLock implements Lock {

    private DBLockDao lockDao;
    private String path;
    private static ThreadLocal<Boolean> ctx = new ThreadLocal<>();

    public DBLock(DBLockDao lockDao, String path) {
        this.lockDao = lockDao;
        this.path = path;
    }

    @Override
    public void lock() {
        System.out.println("Acquiring lock on path=" + path + " by thread: " + Thread.currentThread().getName());
        DatabaseLock lock = lockDao.getLock(path);
        try {
            if(lock == null) {
                lockDao.createLock(path);
                lock = lockDao.getLock(path);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        System.out.println("Lock acquired on path=" + path + " by thread: " + Thread.currentThread().getName());
        try {
            lock.setUpdated(new Date());
        } catch (Exception e) {

        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
