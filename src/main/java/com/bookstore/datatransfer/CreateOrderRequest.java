package com.bookstore.datatransfer;

import java.util.List;

public class CreateOrderRequest {

    private List<BookRequest> books;

    public CreateOrderRequest() {}

    public List<BookRequest> getBooks() {
        return books;
    }
    public void setBooks(List<BookRequest> books) {
        this.books = books;
    }
}