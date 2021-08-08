package com.example.toiec.core.common.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    // Initial session factory hibernate
    private static  SessionFactory buildSessionFactory() {
        try {
            //create a session factory from hibernate configuration
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable e) {
            System.out.println("Initial secction factory fail!");
            throw new ExceptionInInitializerError(e);
        }

    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
