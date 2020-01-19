package com.grocery.henry;

import java.util.Scanner;

import static com.grocery.henry.domain.Product.APPLE;
import static com.grocery.henry.domain.Product.BREAD;
import static com.grocery.henry.domain.Product.MILK;
import static com.grocery.henry.domain.Product.SOUP;
import static java.time.LocalDate.now;

public class CommandLineRunner {
    public static void main(String[] args) {
        Basket basket = new Basket();
        Scanner scan = new Scanner(System.in);

        System.out.println("How many soup tins?: ");
        basket.add(SOUP, scan.nextInt());

        System.out.println("How many loaves of bread?: ");
        basket.add(BREAD, scan.nextInt());

        System.out.println("How many milk bottles?: ");
        basket.add(MILK, scan.nextInt());

        System.out.println("How many apples: ");
        basket.add(APPLE, scan.nextInt());

        System.out.printf("Total price to be paid after discount :  %f", basket.calculate(now()));

    }
}
