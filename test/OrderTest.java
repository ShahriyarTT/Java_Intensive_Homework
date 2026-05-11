import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void shouldCalculateTotalPrice() {

        Book b1 = new Book("A", "AA", 10);
        Book b2 = new Book("B", "BB", 20);

        Order order = new Order(1, List.of(b1, b2));

        assertEquals(30, order.getTotalPrice());
    }

    @Test
    void shouldCompleteOrder() {

        Book b = new Book("A", "AA", 10);

        Order order = new Order(1, List.of(b));

        order.complete();

        assertEquals(Order.Status.COMPLETED, order.getStatus());
    }
}