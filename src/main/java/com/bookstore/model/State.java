package com.bookstore.model;
import java.io.Serializable;
import java.util.List;
import com.bookstore.service.OrdersList;


public class State implements Serializable {

    private List<Book> books;
    private OrdersList ordersList;

    public State(List<Book> books, OrdersList ordersList) {
        this.books = books;
        this.ordersList = ordersList;
    }

    public List<Book> getBooks() {
        return books;
    }

    public OrdersList getOrdersList() {
        return ordersList;
    }

}
