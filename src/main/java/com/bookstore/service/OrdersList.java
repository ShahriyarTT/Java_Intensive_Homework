package com.bookstore.service;
import java.util.ArrayList;
import java.util.List;
import com.bookstore.model.Order;
import com.bookstore.model.Book;

import java.io.Serializable;

import com.bookstore.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrdersList implements Serializable {

/*  Note: deleted due to SQL database to store application state
    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;
*/

/*  Note: deleted due to SQL database to store application state
    public Order openOrder(List<Book> books) {
        Order order = new Order(nextId++, books);
        orders.add(order);
        return order;
    }
*/

    /*
    public Order openOrder(List<Book> books) {
        try (
                Connection conn = DBConnection.getConnection();

                PreparedStatement orderStmt = conn.prepareStatement(
                        // "INSERT INTO orders (status) VALUES (?) RETURNING id"
                        "INSERT INTO orders (status, total_price) VALUES (?, ?) RETURNING id"
                )
        ) {
            orderStmt.setString(1, "OPEN");
            double totalPrice = books.stream()
                    .mapToDouble(Book::getPrice)
                    .sum();

            orderStmt.setDouble(2, totalPrice);

            ResultSet rs = orderStmt.executeQuery();
            rs.next();
            int orderId = rs.getInt("id");

            Order order = new Order(orderId, books);
            return order;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
     */

    public Order openOrder(List<Book> books) {
        try (Connection conn = DBConnection.getConnection()) {

            // 1. create order
            PreparedStatement orderStmt = conn.prepareStatement(
                    "INSERT INTO orders (status, total_price) VALUES (?, ?) RETURNING id"
            );

            orderStmt.setString(1, "OPEN");

            double totalPrice = books.stream()
                    .mapToDouble(Book::getPrice)
                    .sum();

            orderStmt.setDouble(2, totalPrice);

            ResultSet rs = orderStmt.executeQuery();
            rs.next();
            int orderId = rs.getInt("id");

            // 2. insert order items
            String itemSql =
                    "INSERT INTO order_items (order_id, book_id, price) VALUES (?, ?, ?)";

            // PreparedStatement itemStmt = conn.prepareStatement(itemSql);
            try (PreparedStatement itemStmt = conn.prepareStatement(itemSql)) {
                for (Book book : books) {
                    itemStmt.setInt(1, orderId);
                    itemStmt.setInt(2, book.getId());
                    itemStmt.setDouble(3, book.getPrice());
                    itemStmt.addBatch();
                }
                itemStmt.executeBatch();
            }

            /*
            for (Book book : books) {
                itemStmt.setInt(1, orderId);
                itemStmt.setInt(2, book.getId());
                itemStmt.setDouble(3, book.getPrice());
                itemStmt.addBatch();
            }
            itemStmt.executeBatch();
             */

            // return new Order(orderId, books);
            return new Order(orderId, totalPrice, Order.Status.OPEN);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/*  Note: modified due to SQL database to store application state
    public void completeOrder(int id) {
        Order order = findById(id);
        if (order != null) {
            order.complete();
        }
    }
*/

    public void completeOrder(int id) {
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE orders SET status = ? WHERE id = ?"
                )
        ) {
            stmt.setString(1, "COMPLETED");
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/*  Note: modified due to SQL database to store application state
    public void cancelOrder(int id) {
        Order order = findById(id);
        if (order != null) {
            order.cancel();
        }
    }
*/

    public void cancelOrder(int id) {
        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "UPDATE orders SET status = ? WHERE id = ?"
                )
        ) {
            stmt.setString(1, "CANCELLED");
            stmt.setInt(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/*  Note: modified due to SQL database to store application state
    public List<Order> getOrders() {
        return orders;
    }
*/

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        // "SELECT id, status FROM orders"
                        // "SELECT id, status, total_price FROM orders"
                        "SELECT o.id, o.status, o.total_price, " +
                         "oi.book_id, oi.price " +
                         "FROM orders o " +
                         "LEFT JOIN order_items oi ON o.id = oi.order_id"
                );
                ResultSet rs = stmt.executeQuery()
        ) {

        /*
            while (rs.next()) {
                int id = rs.getInt("id");

                // temporary: empty book list for now
                Order order = new Order(id, new ArrayList<>());

                String status = rs.getString("status");

                if ("COMPLETED".equals(status)) {
                    order.complete();
                } else if ("CANCELLED".equals(status)) {
                    order.cancel();
                }

                orders.add(order);
            }
        */
            Map<Integer, Order> map = new HashMap<>();

            while (rs.next()) {


                int id = rs.getInt("id");

                Order order = map.get(id);

                if (order == null) {

                    String status = rs.getString("status");
                    double totalPrice = rs.getDouble("total_price");

                    order = new Order(
                            id,
                            totalPrice,
                            "COMPLETED".equals(status)
                                    ? Order.Status.COMPLETED
                                    : "CANCELLED".equals(status)
                                    ? Order.Status.CANCELLED
                                    : Order.Status.OPEN
                    );

                    map.put(id, order);
                    orders.add(order);
                }

                // books ignored for now (safe)
                /*// set correct status manually (NO logic execution)
                if ("COMPLETED".equals(status)) {
                    // do nothing, just informational
                } else if ("CANCELLED".equals(status)) {
                    // do nothing
                }
                 */

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }

/*  Note: removed completely due to SQL database to store application state
    public void printOrders() {
        orders.forEach(System.out::println);
    }
*/

/*
    private Order findById(int id) {
        for (Order o : orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }
*/

/*  Note: removed completely due to SQL database to store application state
    private Order findById(int id) {
        return orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElse(null);
    }
*/

}