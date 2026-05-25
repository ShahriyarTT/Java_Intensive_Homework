package com.bookstore.database;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("bookstorePU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}