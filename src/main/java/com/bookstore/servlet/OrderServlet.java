    package com.bookstore.servlet;

    import com.bookstore.model.Book;
    import com.bookstore.model.Order;
    import com.bookstore.service.OrdersList;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.bookstore.datatransfer.CreateOrderRequest;
    import com.bookstore.datatransfer.BookRequest;
    // import com.bookstore.database.DBConnection;
    import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
    import com.fasterxml.jackson.databind.SerializationFeature;

    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.servlet.annotation.WebServlet;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.List;
    // import java.sql.Connection;
    // import java.sql.PreparedStatement;
    // import java.sql.ResultSet;
    // import java.sql.SQLException;
    import com.bookstore.database.JpaUtil;
    import jakarta.persistence.EntityManager;

    @WebServlet("/orders")
    public class OrderServlet extends HttpServlet {

        private OrdersList ordersList;
        private ObjectMapper mapper;

        @Override
        public void init() throws ServletException {
            ordersList = new OrdersList();
            mapper = new ObjectMapper();

            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            System.out.println("OrderServlet initialized");
        }

        private Book findBookById(int id) {
            EntityManager em =
                    JpaUtil.getEntityManagerFactory().createEntityManager();

            try {
                return em.find(Book.class, id);

            } finally {
                em.close();
            }
        }

        // GET /api/orders
        /*@Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

            resp.setContentType("application/json");
            mapper.writeValue(
                    resp.getOutputStream(),
                    ordersList.getOrders()
            );
        }

         */
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

            List<Order> orders = ordersList.getOrders();
            System.out.println("Orders size = " + orders.size());
            for (Order o : orders) {
                System.out.println(
                        "Order id = " + o.getId()
                                + ", books = " + o.getBooks().size()
                );
            }

            resp.setContentType("application/json");
            mapper.writeValue(
                    resp.getOutputStream(),
                    orders
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

                // boolean found = false;
                Book book = findBookById(requestedBook.getBookId());

                if (book == null) {
                    resp.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Book not found with id: " + requestedBook.getBookId()
                    );
                    return;
                }
                selectedBooks.add(book);

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