package com.bookstore.controller;

import com.bookstore.datatransfer.BookRequest;
import com.bookstore.datatransfer.CreateOrderRequest;
import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.OrdersList;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrdersList ordersList;
    private final BookRepository bookRepository;

    public OrderController(
            OrdersList ordersList,
            BookRepository bookRepository
    ) {
        this.ordersList = ordersList;
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Order> getOrders() {
        return ordersList.getOrders();
    }

    @PostMapping
    public Order createOrder (
            @RequestBody CreateOrderRequest request
    ) {
        if (request.getBooks() == null || request.getBooks().isEmpty()) {
            throw new RuntimeException("Order must contain at least one book");
        }

        List<Book> books = new ArrayList<>();

        for (BookRequest br : request.getBooks()) {
            Book book = bookRepository.findById(br.getBookId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Book not found with id: "
                                            + br.getBookId()
                            )
                    );
            books.add(book);
        }
        return ordersList.openOrder(books);
    }

    @PutMapping("/{id}/complete")
    public void completeOrder(@PathVariable int id) {
        ordersList.completeOrder(id);
    }

    @PutMapping("/{id}/cancel")
    public void cancelOrder(@PathVariable int id) {
        ordersList.cancelOrder(id);
    }

}