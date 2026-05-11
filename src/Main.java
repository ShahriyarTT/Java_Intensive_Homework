import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        List<Book> availableBooks = new ArrayList<>();

        availableBooks.add(new Book("Sapiens", "Yuval Noah Harari", 10.00));
        availableBooks.add(new Book("The 48 Laws of Power", "Robert Green", 24.99));
        availableBooks.add(new Book("The Art of War", "Sun Tzu", 19.99));
        availableBooks.add(new Book("1984", "George Orwell", 42.50));
        availableBooks.add(new Book("Principles of Economics", "N. Gregory Mankiw", 30.50));

        OrdersList orderList = new OrdersList();

        PlayGround PG = new PlayGround(availableBooks, orderList);
        PG.start();

    }

}
