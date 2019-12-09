package com.example.service.impl;

import java.util.concurrent.locks.Lock;

import com.example.service.LockService;
import com.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ashish Sharma on 08/12/19.
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private LockService lockService;

    @Override
    @Transactional
    public void doTask() {
        Lock lock = lockService.getLock("/task/service");
        lock.lock();
        for(int i=0; i<10; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + ": --: counter: " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Exception in thread: " + Thread.currentThread().getName() + "./n" + e.getMessage());
            }
        }

        lock.unlock();
    }
}
