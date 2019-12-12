package com.example.dao.impl;

import com.example.bean.Order;
import com.example.dao.OrderDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ashish Sharma on 12/12/19.
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void saveOrder(Order order) {
        sessionFactory.getCurrentSession().save(order);
    }
}
