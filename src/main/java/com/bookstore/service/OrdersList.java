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

            // 1. calculate total price
            double totalPrice = books.stream()
                    .mapToDouble(Book::getPrice)
                    .sum();

            // 2. create order entity
            Order order = new Order(totalPrice, Order.Status.OPEN);

            // 3. persist order
            em.persist(order);
            tx.commit();
            return order;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
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
                    "FROM Order",
                    Order.class
            ).getResultList();

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            em.close();
        }
    }

}