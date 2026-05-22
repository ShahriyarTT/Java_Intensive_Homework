package com.bookstore.model;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.service.OrdersList;

public class BookTest {

    @Test
    void shouldReturnCorrectPrice() {

        Book book = new Book(1, "1984", "George Orwell", 42.50);

        assertEquals(42.50, book.getPrice());
    }
}
