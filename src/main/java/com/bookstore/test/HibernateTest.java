package com.bookstore.test;

import com.bookstore.database.JpaUtil;
import com.bookstore.model.Book;

import jakarta.persistence.EntityManager;
import java.util.List;

public class HibernateTest {

    public static void main(String[] args) {

        EntityManager em =
                JpaUtil.getEntityManagerFactory().createEntityManager();

        try {

            // =========================
            // 1. SHOW ALL ACTIVE BOOKS
            // =========================
            System.out.println("\n=== INITIAL ACTIVE BOOKS ===");

            List<Book> books = em.createQuery(
                    "from Book where available = true",
                    Book.class
            ).getResultList();

            books.forEach(System.out::println);


            // =========================
            // 2. SOFT DELETE (DISABLE BOOK)
            // =========================
            em.getTransaction().begin();

            Book book = em.find(Book.class, 1);

            if (book != null) {
                System.out.println("\n=== SOFT DELETING BOOK ===");
                System.out.println(book);

                book.setAvailable(false);
            }

            em.getTransaction().commit();


            // =========================
            // 3. VERIFY AFTER DELETE
            // =========================
            System.out.println("\n=== AFTER SOFT DELETE ===");

            books = em.createQuery(
                    "from Book where available = true",
                    Book.class
            ).getResultList();

            books.forEach(System.out::println);


            // =========================
            // 4. RESTORE BOOK
            // =========================
            em.getTransaction().begin();

            Book book2 = em.find(Book.class, 1);

            if (book2 != null) {
                System.out.println("\n=== RESTORING BOOK ===");
                System.out.println(book2);

                book2.setAvailable(true);
            }

            em.getTransaction().commit();


            // =========================
            // 5. FINAL CHECK
            // =========================
            System.out.println("\n=== FINAL ACTIVE BOOKS ===");

            books = em.createQuery(
                    "from Book where available = true",
                    Book.class
            ).getResultList();

            books.forEach(System.out::println);

        } catch (Exception e) {

            em.getTransaction().rollback();
            e.printStackTrace();

        } finally {

            em.close();
        }
    }
}