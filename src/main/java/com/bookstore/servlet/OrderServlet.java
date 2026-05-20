package com.bookstore.servlet;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.service.OrdersList;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class OrderServlet extends HttpServlet {

    private OrdersList ordersList;
    private ObjectMapper mapper;

    @Override
    public void init() throws ServletException {

        ordersList = new OrdersList();
        mapper = new ObjectMapper();

        System.out.println("OrderServlet initialized");
    }

    // GET /api/orders
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json");
        mapper.writeValue(
                resp.getOutputStream(),
                ordersList.getOrders()
        );
    }

    // POST /api/orders
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        List<Book> books = List.of(
                new Book(
                        "Sapiens",
                        "Yuval Noah Harari",
                        10.0
                )
        );

        Order order = ordersList.openOrder(books);

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_CREATED);

        mapper.writeValue(
                resp.getOutputStream(),
                order
        );
    }

    // PUT /api/orders?id=1&action=complete
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(
                req.getParameter("id")
        );

        String action =
                req.getParameter("action");

        if ("complete".equals(action)) {
            ordersList.completeOrder(id);
        } else if ("cancel".equals(action)) {
            ordersList.cancelOrder(id);
        }

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}