package com.example.service.impl;

import java.util.concurrent.locks.Lock;

import com.example.bean.Order;
import com.example.dao.OrderDao;
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

    @Autowired
    private OrderDao orderDao;

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
        Order order = new Order();
        order.setCode("Order: " + System.currentTimeMillis());
        orderDao.saveOrder(order);

        lock.unlock();
    }
}
