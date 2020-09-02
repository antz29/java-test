package com.jfsoftware.henrys.cli;

import com.jfsoftware.henrys.Cart;
import com.jfsoftware.henrys.model.Item;
import com.jfsoftware.henrys.model.Product;

import java.util.Scanner;

import static java.lang.Integer.valueOf;

public final class CommandLineInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cart shoppingCart = new Cart();

        int ch;
        while (true) {
            System.out.println("Welcome to Henry's store.\nMenu: \n0) " +
                    "Quit \n1) " +
                    "Add item in shopping basket \n2) " +
                    "Add days from now for purchase \n3) " +
                    "Print receipt");

            ch = sc.nextInt();

            switch (ch) {
                case 0:
                    System.out.println("\n" + "Thanks for shopping with us. See you again soon!");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Enter product: [Soup, Bread, Milk, Apple]");
                    String product = sc.next();
                    shoppingCart.addItem(new Item(Product.fromString(product)));
                    break;
                case 2:
                    System.out.println("Enter day to purchase goods, i.e. 0 for today, 1 for tomorrow etc");
                    String daysFromNow = sc.next();
                    shoppingCart.setDaysFromNowToBuy(valueOf(daysFromNow));
                    break;
                case 3:
                    new Receipt(shoppingCart).print();
                    System.exit(0);
                    break;
            }
        }
    }
}