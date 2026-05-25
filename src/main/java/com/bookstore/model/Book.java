package com.bookstore.model;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String author;
    private double price;
    private boolean available = true;

    public Book(String title, String author, double price) {
        // this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Title: " + title +
                " | Author: " + author +
                " | Price: " + price;
    }

    public double getPrice() {
        return price;
    }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

}


