package com.bookstore.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class HibernateInitListener implements ServletContextListener {

    public static EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("bookstorePU");
        System.out.println("Hibernate started!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null) emf.close();
    }
}

