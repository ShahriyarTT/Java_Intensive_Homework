package com.bookstore.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private double totalPrice;
    private LocalDateTime openedAt;
    private LocalDateTime closedAt;

    public Order() {
    }

    // constructor
    public Order(double totalPrice, Status status) {
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

    // Enum
    public enum Status {
        OPEN,
        COMPLETED,
        CANCELLED
    }

    // toString
    @Override
    public String toString() {
        return "Order #" + id +
                " | Total Price: " + totalPrice +
                " | Status: " + status +
                " | Opened at: " + openedAt +
                " | Closed at: " + closedAt;
    }
}