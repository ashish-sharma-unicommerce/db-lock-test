package com.example.service.impl;

import java.util.concurrent.locks.Lock;

import com.example.DBLock;
import com.example.dao.DBLockDao;
import com.example.service.LockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ashish Sharma on 08/12/19.
 */
@Service
public class LockServiceImpl implements LockService {

    @Autowired
    private DBLockDao dbLockDao;

    @Override
    public Lock getLock(String lockKey) {
        LockingContext lockingContext = LockingContext.current();
        Lock lock = lockingContext.getLock(lockKey);
        if (lock == null) {
            lock = new DBLock(dbLockDao, lockKey);
            lockingContext.addLock(lockKey, lock);
        }
        return lock;
    }
}
