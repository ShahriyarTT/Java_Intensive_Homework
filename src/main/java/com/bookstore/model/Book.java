package com.bookstore.model;

import java.io.Serializable;

public class Book implements Serializable  {

    private int id;
    private String title;
    private String author;
    private double price;
    private boolean available = true;

    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
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


