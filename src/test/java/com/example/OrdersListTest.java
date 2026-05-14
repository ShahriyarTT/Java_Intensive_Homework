package com.example;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrdersListTest {

    @Test
    void shouldOpenOrder() {

        OrdersList orders = new OrdersList();

        Book book = new Book("A", "AA", 10);

        orders.openOrder(List.of(book));

        assertEquals(1, orders.getOrders().size());
    }
}