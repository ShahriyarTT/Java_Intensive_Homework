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

-- Sample books
INSERT INTO books (title, author, price, available)
VALUES
    ('Sapiens', 'Yuval Noah Harari', 10.00, TRUE),
    ('The 48 Laws of Power', 'Robert Greene', 24.99, TRUE),
    ('The Art of War', 'Sun Tzu', 19.99, TRUE),
    ('1984', 'George Orwell', 42.50, TRUE),
    ('Principles', 'Ray Dalio', 30.50, TRUE),
    ('Getting Things Done', 'David Allen', 19.99, TRUE),
    ('Rich Dad Poor Dad', 'Robert Kiyosaki', 19.99, TRUE),
    ('How Google Works', 'Eric Schmidt', 19.99, TRUE),
    ('Endurance', 'Alfred Lansing', 19.99, TRUE),
    ('Telling Lies', 'Paul Ekman', 19.99, TRUE),
    ('The Hidden Life of Trees', 'Peter Wohlleben', 19.99, TRUE),
    ('Muhammad', 'Martin Lings', 19.99, TRUE),
    ('The Prince', 'Niccolo Machiavelli', 19.99, TRUE),
    ('Clean Architecture', 'Robert C. Martin', 30.00, TRUE);

-- Many-to-many join table for order items
CREATE TABLE order_items (
                             order_id INT REFERENCES orders(id) ON DELETE CASCADE,
                             book_id INT REFERENCES books(id) ON DELETE CASCADE,
                             PRIMARY KEY (order_id, book_id)
);