package com.bookstore.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)

    private Status status;

    @Column(name = "totalprice")
    private double totalPrice;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<Book> books = new ArrayList<>();

    public Order() {
    }

    // constructor
    public Order(List<Book> books,
                 double totalPrice,
                 Status status) {

        this.books = books;
        this.totalPrice = totalPrice;
        this.status = status;
        this.openedAt = LocalDateTime.now();
    }

    // Getters
    public int getId() {return id;    }
    public Status getStatus() {return status;    }
    public double getTotalPrice() {return totalPrice;    }
    public LocalDateTime getOpenedAt() {return openedAt;    }
    public LocalDateTime getClosedAt() {return closedAt;    }
    public List<Book> getBooks() {return books;    }

    // Business logic
    public void complete() {
        if (status != Status.OPEN) return;
        status = Status.COMPLETED;
        closedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status != Status.OPEN) return;
        status = Status.CANCELLED;
        closedAt = LocalDateTime.now();
    }

    public enum Status {
        OPEN,
        COMPLETED,
        CANCELLED
    }

    @Override
    public String toString() {
        return "Order #" + id +
                " | Books count: " + books.size() +
                " | Total Price: " + totalPrice +
                " | Status: " + status;
    }
}