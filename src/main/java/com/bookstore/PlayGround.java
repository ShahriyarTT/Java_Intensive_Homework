package com.bookstore;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

import com.bookstore.model.Book;
import com.bookstore.model.Order;
import com.bookstore.service.OrdersList;
import com.bookstore.model.State;

public class PlayGround {

    private List<Book> availableBooks;
    private OrdersList orderList;
    private Scanner scanner;

    private AppConfigur config;

    public PlayGround(List<Book> availableBooks, OrdersList orderList, AppConfigur config) {
        this.availableBooks = availableBooks;
        this.orderList = orderList;
        this.config = config;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        printWelcome();
        printBooks();

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            if (choice == 0) break;

            handleChoice(choice);
        }
    }

    private void printWelcome() {
        System.out.println("*************");
        System.out.println("WELCOME to USEFUL BOOKS' STORE");
        System.out.println("*************");
    }

    private void printBooks() {
        System.out.println("LIST of AVAILABLE BOOKS");
    /*
        for (int i = 0; i < availableBooks.size(); i++) {
            System.out.println(i + 1 + ": " + availableBooks.get(i));
        }
   */
        IntStream.range(0, availableBooks.size())
                .forEach(i -> System.out.println((i + 1) + ": " + availableBooks.get(i)));

        System.out.println("*************");
    }

    private void printMenu() {
        System.out.println("\n1. Open order");
        System.out.println("2. Complete order");
        System.out.println("3. Cancel order");
        System.out.println("4. List orders");
        System.out.println("0. Exit");
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> openOrder();
            case 2 -> completeOrder();
            case 3 -> cancelOrder();
            case 4 -> orderList.printOrders();
        }
    }

    private void openOrder() {
        List<Book> selectedBooks = new ArrayList<>();

        System.out.println("Select books (1-" + availableBooks.size() + ", 0 to finish):");

        while (true) {
            int b = scanner.nextInt();
            if (b == 0) break;

            if (b >= 1 && b <= availableBooks.size()) {
                Book selectedBook = availableBooks.get(b - 1);

                if (config.isAvailabilityChangeEnabled()) {

                    if (!selectedBook.isAvailable()) {
                        System.out.println("Book is not available.");
                        continue;
                    }

                    selectedBook.setAvailable(false);
                }

                selectedBooks.add(selectedBook);
                System.out.println("Added.");
            } else {
                System.out.println("Invalid.");
            }
        }

        if (selectedBooks.isEmpty()) {
            System.out.println("Order must contain at least one book.");
            return;
        }

        Order newOrder = orderList.openOrder(selectedBooks);
        System.out.println("Created: " + newOrder);
    }

    private void completeOrder() {
        System.out.print("Enter ID: ");
        orderList.completeOrder(scanner.nextInt());
    }

    private void cancelOrder() {
        System.out.print("Enter ID: ");
        orderList.cancelOrder(scanner.nextInt());
    }




}
