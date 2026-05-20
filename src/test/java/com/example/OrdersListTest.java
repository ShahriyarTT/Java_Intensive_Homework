package com.bookstore.service;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.service.OrdersList;

public class OrdersListTest {

    @Test
    void shouldOpenOrder() {

        OrdersList orders = new OrdersList();

        Book book = new Book("A", "AA", 10);

        orders.openOrder(List.of(book));

        assertEquals(1, orders.getOrders().size());
    }
}