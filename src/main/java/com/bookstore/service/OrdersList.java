package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class OrdersList {

    private final OrderRepository orderRepository;

    public OrdersList(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order openOrder(List<Book> books) {

        double totalPrice = books.stream()
                .mapToDouble(Book::getPrice)
                .sum();

        // validate availability
        for (Book b : books) {
            if (!b.isAvailable()) {
                throw new RuntimeException("Book not available: " + b.getTitle());
            }
        }

        Order order = new Order(
                books,
                totalPrice,
                Order.Status.OPEN
        );

        return orderRepository.save(order);
    }


    public void completeOrder(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.complete();
        orderRepository.save(order); // optional but explicit
    }

    public void cancelOrder(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        order.cancel();
        orderRepository.save(order); // optional but explicit
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}