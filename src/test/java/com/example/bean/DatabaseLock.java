package com.example.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Ashish Sharma on 08/12/19.
 */
@Entity
@Table(name =  "db_lock")
public class DatabaseLock {
    private int id;
    private String lockPath;
    private Date updated = new Date();

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "lock_path", length = 45, unique = true)
    public String getLockPath() {
        return lockPath;
    }

    public void setLockPath(String lockPath) {
        this.lockPath = lockPath;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
