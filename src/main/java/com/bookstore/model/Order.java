package com.bookstore.model;

import java.time.LocalDateTime;
import java.util.List;

import java.io.Serializable;

public class Order implements Serializable {

    private int id;
    private List<Book> books;
    private double totalPrice;
    private Status status;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    public Order(int id, List<Book> books) {
        this.id = id;
        this.books = books;
        this.status = Status.OPEN;
        this.openedAt = LocalDateTime.now();

        this.totalPrice = 0;
        for (Book book : books) {
            this.totalPrice += book.getPrice();
        }
    }

    @Override
    public String toString() {
        return "Order #" + id +
               " | Books count: " + books.size() + " item(s)" +
               " | Total Price: " + totalPrice + " USD" +
               " | Status: " + status +
               " | Opened at: " + openedAt +
               " | Closed/Completed at: " + closedAt;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public enum Status {
        OPEN,
        COMPLETED,
        CANCELLED
    }

    public void complete() {
        if (status != Status.OPEN) return;
        status = Status.COMPLETED;
        closedAt = LocalDateTime.now();
        System.out.println("Order completed.");
    }

    public void cancel() {
        if (status != Status.OPEN) return;
        status = Status.CANCELLED;
        closedAt = LocalDateTime.now();
        System.out.println("Order cancelled.");
    }

}

