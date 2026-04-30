import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Main {

    public static void main(String[] args) {

    //-----------------------------------------------------------------------------------------------------------
    /*
        Homework #1
        ● Pick a project
        ● Implement required functionality
        ● User should be able to interact with the application using console interface:
          apartment register 1 1
          order open 123
        ● You can omit tests for now
        ● You can store state in memory for now
        ● Think a little bit ahead about what can be changed in the future. Plan an architecture
          to support such changes.
        ● Any frameworks or libraries - forbidden!
    */

    /*
        Bookstore
        ● Open a new order given total price and at least one book
        ● Cancel an order given it’s currently opened
        ● Complete an order given it’s currently opened
        ● List orders (with pagination) sorted by ID, total price, opening timestamp, closing
          timestamp, status
    */

    // Welcome Message
    System.out.println("*************");
    System.out.println("WELCOME to USEFUL BOOKS' STORE");
    System.out.println("*************");

    // Create and Print a List of Books
    System.out.println("LIST of AVAILABLE BOOKS");

    List<Book> availableBooks = new ArrayList<>();

    availableBooks.add(new Book("Sapiens", "Yuval Noah Harari", 10.00));
    availableBooks.add(new Book("The 48 Laws of Power", "Robert Green", 24.99));
    availableBooks.add(new Book("The Art of War", "Sun Tzu", 19.99));
    availableBooks.add(new Book("1984", "George Orwell", 42.50));
    availableBooks.add(new Book("Principles of Economics", "N. Gregory Mankiw", 30.50));

    for (int i = 0; i < availableBooks.size(); i++) {
        System.out.println(i + 1 + ": " + availableBooks.get(i));
    }

    System.out.println("*************");

    //
    OrdersList orderList = new OrdersList();

    Scanner scanner = new Scanner(System.in);

    while (true) {
        System.out.println("\n1. Open order");
        System.out.println("2. Complete order");
        System.out.println("3. Cancel order");
        System.out.println("4. List orders");
        System.out.println("0. Exit");

        int choice = scanner.nextInt();

        if (choice == 0) break;

        switch (choice) {

            case 1:
                // Select Books into a List
                List<Book> selectedBooks = new ArrayList<>();

                System.out.println("Select books (1-" + availableBooks.size() + ", 0 to finish):");

                while (true) {
                    int b = scanner.nextInt();
                    if (b == 0) break;

                    if (b >= 1 && b <= availableBooks.size()) {
                        selectedBooks.add(availableBooks.get(b - 1));
                        System.out.println("Added.");
                    } else {
                        System.out.println("Invalid.");
                    }
                }

                if (selectedBooks.isEmpty()) {
                    System.out.println("Order must contain at least one book.");
                    break;
                }

                Order newOrder = orderList.openOrder(selectedBooks);
                System.out.println("Created: " + newOrder);
                break;

            case 2:
                System.out.print("Enter ID: ");
                orderList.completeOrder(scanner.nextInt());
                break;

            case 3:
                System.out.print("Enter ID: ");
                orderList.cancelOrder(scanner.nextInt());
                break;

            case 4:
                orderList.printOrders();
                break;
        }
    }

    scanner.close();

    }
}
