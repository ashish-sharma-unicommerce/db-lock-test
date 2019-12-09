package com.example.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;
import com.example.dao.DBLockDao;
import com.example.dao.impl.DBLockDaoImpl;
import com.example.service.LockService;
import com.example.service.TaskService;
import com.example.service.impl.LockServiceImpl;
import com.example.service.impl.TaskServiceImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Ashish Sharma on 06/12/19.
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class TestConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean("sessionFactoryBean")
    @Primary
    public LocalSessionFactoryBean getSessionFactoryBean() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(new String[] { "com.example.bean" });
        sessionFactory.setAnnotatedPackages(new String[] { "com.example.bean" });
        sessionFactory.setHibernateProperties(getHibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager hibernateTransactionManager(){
        return new HibernateTransactionManager(sessionFactory());
    }

    @Bean("sessionFactory")
    public SessionFactory sessionFactory() {
        return getSessionFactoryBean().getObject();
    }


    @Bean("lockSerive")
    public LockService lockService() throws IOException {
        return new LockServiceImpl();
    }


    @Bean("takService")
    public TaskService takService() throws IOException {
        return new TaskServiceImpl();
    }

    @Bean("dbLockDao")
    public DBLockDao dbLockDao() throws IOException {
        return new DBLockDaoImpl();
    }


    private Properties getHibernateProperties() {
        Properties p = new Properties();
        //p.putAll(prop.getProperties());
        //p.put(AvailableSettings.HBM2DDL_AUTO, "update");
        p.put(AvailableSettings.SHOW_SQL, true);
        p.put(AvailableSettings.FORMAT_SQL, true);
        return p;
    }
}
