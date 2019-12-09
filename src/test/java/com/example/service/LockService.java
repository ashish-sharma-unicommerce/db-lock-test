package com.example.service;

import java.util.concurrent.locks.Lock;

/**
 * @author Ashish Sharma on 08/12/19.
 */
public interface LockService {

    Lock getLock(String path);
}
