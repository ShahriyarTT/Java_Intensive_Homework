package com.example;

import java.io.Serializable;

public class Book implements Serializable  {

    private String title;
    private String author;
    private double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Title: " + title +
               " | Author: " + author +
               " | Price: " + price;
    }

    public double getPrice() {
        return price;
    }


    private boolean available = true;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}


