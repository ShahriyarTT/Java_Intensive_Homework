package com.example;
import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class OrdersList implements Serializable {

    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;

    public Order openOrder(List<Book> books) {
        Order order = new Order(nextId++, books);
        orders.add(order);
        return order;
    }

    public void completeOrder(int id) {
        Order order = findById(id);
        if (order != null) {
            order.complete();
        }
    }

    public void cancelOrder(int id) {
        Order order = findById(id);
        if (order != null) {
            order.cancel();
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void printOrders() {
    /*
        for (Order o : orders) {

            System.out.println(o);
        }
    */
        orders.forEach(System.out::println);
    }

/*
    private Order findById(int id) {
        for (Order o : orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }
*/
    private Order findById(int id) {
        return orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }

}