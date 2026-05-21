package com.bookstore.servlet;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.service.OrdersList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookstore.datatransfer.CreateOrderRequest;
import com.bookstore.datatransfer.BookRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private OrdersList ordersList;
    private ObjectMapper mapper;
    private List<Book> availableBooks;

    @Override
    public void init() throws ServletException {

        ordersList = new OrdersList();
        mapper = new ObjectMapper();
        availableBooks = new ArrayList<>();

        availableBooks.add(new Book("Sapiens", "Yuval Noah Harari", 10.00));
        availableBooks.add(new Book("The 48 Laws of Power", "Robert Greene", 24.99));
        availableBooks.add(new Book("The Art of War", "Sun Tzu", 19.99));
        availableBooks.add(new Book("1984", "George Orwell", 42.50));
        availableBooks.add(new Book("Principles of Economics", "N. Gregory Mankiw", 30.50));
        availableBooks.add(new Book("Getting Things Done", "David Allen", 19.99));
        availableBooks.add(new Book("Rich Dad Poor Dad", "Robert Kiyosaki", 19.99));
        availableBooks.add(new Book("How google works", "Eric Schmidt", 19.99));
        availableBooks.add(new Book("Endurance", "Alfred Lansing", 19.99));
        availableBooks.add(new Book("Telling lies", "Paul Ekman", 19.99));
        availableBooks.add(new Book("The Hidden Persuaders", "Vance Packard", 19.99));
        availableBooks.add(new Book("Muhammad", "Martin Lings", 19.99));
        availableBooks.add(new Book("The Prince", "Niccolo Machiavelli", 19.99));


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
            throws ServletException, IOException {

        CreateOrderRequest request = mapper.readValue(req.getInputStream(), CreateOrderRequest.class);
        if (request.getBooks() == null || request.getBooks().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Book list cannot be empty");
            return;
        }

        List<Book> selectedBooks = new ArrayList<>();

        for (BookRequest requestedBook : request.getBooks()) {

            boolean found = false;

            for (Book book : availableBooks) {

                if (book.getTitle().equalsIgnoreCase(requestedBook.getTitle())
                        && book.getAuthor().equalsIgnoreCase(requestedBook.getAuthor())) {
                    selectedBooks.add(book);
                    found = true;
                    break;
                }
            }

            if (!found) {
                resp.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Book not found: " + requestedBook.getTitle()
                                + " by " + requestedBook.getAuthor()
                );
                return;
            }
        }

        Order order = ordersList.openOrder(selectedBooks);

        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), order);
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