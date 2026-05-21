/*
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

        Book book = new Book(1, "A", "AA", 10);

        // orders.openOrder(List.of(book)); deleted due to SQL DB
        Order order = orders.openOrder(List.of(book));

        // assertEquals(1, orders.getOrders().size()); deleted due to SQL DB
        assertEquals(order.getId() > 0, true);

    }
}

 */