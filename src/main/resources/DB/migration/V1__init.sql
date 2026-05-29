-- Table for books
CREATE TABLE books (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255),
                       price DOUBLE PRECISION NOT NULL,
                       available BOOLEAN DEFAULT TRUE
);

-- Table for orders
CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        status VARCHAR(20),
                        totalprice DOUBLE PRECISION,
                        opened_at TIMESTAMP,
                        closed_at TIMESTAMP
);

-- Many-to-many join table for order items
CREATE TABLE order_items (
                             order_id INT REFERENCES orders(id) ON DELETE CASCADE,
                             book_id INT REFERENCES books(id) ON DELETE CASCADE,
                             PRIMARY KEY (order_id, book_id)
);