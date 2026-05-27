package com.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import com.bookstore.model.Order;
import com.bookstore.model.Book;
import java.io.Serializable;
// import com.bookstore.database.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Service;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrdersList implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public Order openOrder(List<Book> books) {
        double totalPrice = books.stream()
                .mapToDouble(Book::getPrice)
                .sum();

        Order order = new Order(books, totalPrice, Order.Status.OPEN);
        em.persist(order);
        return order;
    }

    public void completeOrder(int id) {
        Order order = em.find(Order.class, id);
        if (order != null) {
            order.complete();
        }
    }

    public void cancelOrder(int id) {
        Order order = em.find(Order.class, id);
        if (order != null) {
            order.cancel();
        }
    }

    public List<Order> getOrders() {
        return em.createQuery(
                "SELECT DISTINCT o FROM Order o JOIN FETCH o.books",
                Order.class
        ).getResultList();
    }



}