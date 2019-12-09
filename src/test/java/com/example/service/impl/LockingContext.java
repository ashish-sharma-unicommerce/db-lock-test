package com.example.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author Ashish Sharma on 08/12/19.
 */
public class LockingContext {
    private static ThreadLocal<LockingContext> ctx = new ThreadLocal<>();

    private Map<String, Lock> keyToLock          = new HashMap<>();
    private Map<String, ReadWriteLock> keyToReadWriteLock = new HashMap<>();

    public LockingContext() {
    }

    public static LockingContext current() {
        LockingContext requestContext = ctx.get();
        if (requestContext == null) {
            requestContext = new LockingContext();
            ctx.set(requestContext);
        }
        return requestContext;
    }

    public ReadWriteLock getReadWriteLock(String key) {
        return keyToReadWriteLock.get(key);
    }

    public void addReadWriteLock(String key, ReadWriteLock lock) {
        keyToReadWriteLock.put(key, lock);
    }

    public Lock getLock(String key) {
        return keyToLock.get(key);
    }

    public void addLock(String key, Lock lock) {
        keyToLock.put(key, lock);
    }

    public void clear() {
        ctx.remove();
    }
}
