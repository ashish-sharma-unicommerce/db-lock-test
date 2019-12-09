package com.example.dao;

import com.example.bean.DatabaseLock;

/**
 * @author Ashish Sharma on 09/12/19.
 */
public interface DBLockDao {

    DatabaseLock getLock(String path);
    DatabaseLock createLock(String path);
}
