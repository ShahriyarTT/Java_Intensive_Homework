
import java.util.ArrayList;
import java.util.List;

public class OrdersList {

    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;

    public Order openOrder(List<Book> books) {
        Order order = new Order(nextId++, books);
        orders.add(order);
        return order;
    }

    public void completeOrder(int id) {
        Order order = findById(id);
        if (order != null) {
            order.complete();
        }
    }

    public void cancelOrder(int id) {
        Order order = findById(id);
        if (order != null) {
            order.cancel();
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void printOrders() {
        for (Order o : orders) {
            System.out.println(o);
        }
    }

    private Order findById(int id) {
        for (Order o : orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

}