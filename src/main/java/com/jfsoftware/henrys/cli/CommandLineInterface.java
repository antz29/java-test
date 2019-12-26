package com.jfsoftware.henrys.cli;

import com.jfsoftware.henrys.ShoppingContext;
import com.jfsoftware.henrys.model.Product;
import com.jfsoftware.henrys.model.StockItem;
import com.jfsoftware.henrys.offer.AppleOffer;
import com.jfsoftware.henrys.offer.BreadSoupOffer;
import com.jfsoftware.henrys.offer.Offer;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Integer.valueOf;
import static java.util.Arrays.asList;

public class CommandLineInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Set<Offer> offers = new HashSet<>(asList(new AppleOffer(), new BreadSoupOffer()));
        ShoppingContext shoppingContext = new ShoppingContext(offers);

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
                    shoppingContext.addItemToBasket(new StockItem(Product.fromString(product)));
                    break;
                case 2:
                    System.out.println("Enter day to purchase goods, i.e. 0 for today, 1 for tomorrow etc");
                    String daysFromNow = sc.next();
                    shoppingContext.setDaysFromNowToBuy(valueOf(daysFromNow));
                    break;
                case 3:
                    shoppingContext.printReceipt();
                    System.exit(0);
                    break;
            }
        }
    }
}