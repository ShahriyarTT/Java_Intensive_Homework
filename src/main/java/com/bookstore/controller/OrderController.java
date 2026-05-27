package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.service.OrdersList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class OrderController {

    private final OrdersList ordersList;

    public OrderController(OrdersList ordersList) {
        this.ordersList = ordersList;
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return ordersList.getOrders();
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody List<Book> books) {
        return ordersList.openOrder(books);
    }

}