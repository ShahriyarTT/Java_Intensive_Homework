package com.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import com.bookstore.model.Order;
import com.bookstore.model.Book;
import java.io.Serializable;
import com.bookstore.database.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class OrdersList implements Serializable {

    public Order openOrder(List<Book> books) {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            double totalPrice = books.stream()
                    .mapToDouble(Book::getPrice)
                    .sum();

            Order order = new Order(books, totalPrice, Order.Status.OPEN);

            em.persist(order);

            tx.commit();

            // 🔥 IMPORTANT: reload FULL entity with books initialized
            Order savedOrder = em.createQuery(
                            "SELECT o FROM Order o JOIN FETCH o.books WHERE o.id = :id",
                            Order.class)
                    .setParameter("id", order.getId())
                    .getSingleResult();

            return savedOrder;

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);

        } finally {
            em.close();
        }
    }

    public void completeOrder(int id) {

        EntityManager em =
                JpaUtil.getEntityManagerFactory().createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {
            tx.begin();

            // find by primary key
            Order order = em.find(Order.class, id);
            if (order != null) {
                order.complete();
            }
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);

        } finally {
            em.close();
        }
    }

    public void cancelOrder(int id) {

        EntityManager em =
                JpaUtil.getEntityManagerFactory().createEntityManager();

        EntityTransaction tx =
                em.getTransaction();

        try {
            tx.begin();

            Order order = em.find(Order.class, id);
            if (order != null) {
                order.cancel();
            }
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException(e);

        } finally {
            em.close();
        }
    }

    public List<Order> getOrders() {
        EntityManager em =
                JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT DISTINCT o FROM Order o JOIN FETCH o.books",
                    Order.class
            ).getResultList();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            em.close();
        }
    }



}