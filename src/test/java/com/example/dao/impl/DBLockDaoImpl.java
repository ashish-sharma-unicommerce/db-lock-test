package com.example.dao.impl;

import java.util.List;

import com.example.bean.DatabaseLock;
import com.example.dao.DBLockDao;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author Ashish Sharma on 09/12/19.
 */
@Repository
@Transactional
public class DBLockDaoImpl implements DBLockDao {
    @Autowired
    private SessionFactory sessionFactory;

    public DatabaseLock getLock(String path) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("select id from DatabaseLock where lockPath = :path");
            query.setParameter("path", path);
            List<Integer> id = query.list();
            if(!CollectionUtils.isEmpty(id)) {
                query = sessionFactory.getCurrentSession().createQuery(" select d from DatabaseLock d where d.id = :lockId");
                query.setParameter("lockId", id.get(0));
                query.setLockMode("d", LockMode.PESSIMISTIC_WRITE);
                List<DatabaseLock> list = query.list();
                if(list.size() > 0) {
                    return  list.get(0);
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    public DatabaseLock createLock(String path) {
        DatabaseLock lock = new DatabaseLock();
        lock.setLockPath(path);
        sessionFactory.getCurrentSession().merge(lock);
        sessionFactory.getCurrentSession().flush();
        return lock;

    }
}
